package com.yodha.e_com.services;

import com.yodha.e_com.dto.CartRequestDto;
import com.yodha.e_com.dto.CartResponseDto;
import com.yodha.e_com.entities.Cart;
import com.yodha.e_com.entities.CartItem;
import com.yodha.e_com.entities.Product;
import com.yodha.e_com.exception.ResourceNotFoundException;
import com.yodha.e_com.mapper.CartMapper;
import com.yodha.e_com.repository.CartRepo;
import com.yodha.e_com.repository.ProductRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductRepo productRepo;

    public CartResponseDto createCart(String email, CartRequestDto cartRequestDto) {
        List<CartItem> incomingItems = cartRequestDto.getCartItems().stream().map(itemDto -> {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemDto.getProductId()));

            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setProductName(product.getName());
            cartItem.setQuantity(itemDto.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItem.setImage(product.getImages().get(0));
            return cartItem;
        }).toList();

        // Step 2: Fetch existing cart or create new
        Cart cart = cartRepo.findByEmail(email).orElseGet(() -> {
            Cart c = new Cart();
            c.setEmail(email);
            return c;
        });

        // Step 3: Merge incoming items with existing ones
        List<CartItem> existingItems = cart.getCartItems();
        if (existingItems == null) {
            existingItems = new ArrayList<>();
        }

        for (CartItem incoming : incomingItems) {
            Optional<CartItem> existingOpt = existingItems.stream()
                    .filter(e -> e.getProductId().equals(incoming.getProductId()))
                    .findFirst();

            if (existingOpt.isPresent()) {
                CartItem existing = existingOpt.get();
                existing.setQuantity(existing.getQuantity() + incoming.getQuantity());
                existing.setPrice(incoming.getPrice());
                existing.setProductName(incoming.getProductName());
                existing.setImage(incoming.getImage());
            } else {
                existingItems.add(incoming);
            }
        }

        // Step 4: Recalculate total
        double totalPrice = existingItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cart.setCartItems(existingItems);
        cart.setTotalPrice(totalPrice);

        // Step 5: Save & return
        Cart saved = cartRepo.save(cart);
        return cartMapper.toCartResponseDto(saved);
    }

    public CartResponseDto getCartByEmail(String email) {
        Cart cart = cartRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for email: " + email));
        return cartMapper.toCartResponseDto(cart);
    }

    public void clearCartByEmail(String email) {
        Cart cart = cartRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for email: " + email));
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());
        cartRepo.save(cart);
    }

    public CartResponseDto removeItemFromCart(String email, ObjectId productId) {
    Cart cart = cartRepo.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for email: " + email));

    List<CartItem> cartItems = cart.getCartItems();
    if (cartItems != null) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getProductId().equals(productId)) {
                    cartItems.remove(i);
                    break;
            }
        }
    }

    double totalPrice = cartItems == null ? 0.0 :
            cartItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
    cart.setCartItems(cartItems);
    cart.setTotalPrice(totalPrice);
    Cart updatedCart = cartRepo.save(cart);
    return cartMapper.toCartResponseDto(updatedCart)
    ;
}

    public CartResponseDto updateCart(String email, ObjectId productId, int quantity) {
    if (quantity != 1 && quantity != -1) {
            throw new IllegalArgumentException("Quantity must be either 1 (increment) or -1 (decrement).");
        }
    Cart cart = cartRepo.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for email: " + email));


    List<CartItem> cartItems = cart.getCartItems();
    if (cartItems != null) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getProductId().equals(productId)) {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
                    cartItems.remove(i);
                }
                break;
            }
        }
    }else {
        throw new ResourceNotFoundException("No items in cart to update.");
    }
    double totalPrice = cartItems.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();

    cart.setCartItems(cartItems);
    cart.setTotalPrice(totalPrice);
    Cart updatedCart = cartRepo.save(cart);
    return cartMapper.toCartResponseDto(updatedCart);
}

}
