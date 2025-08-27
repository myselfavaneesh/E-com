package com.yodha.e_com.controller;

import com.yodha.e_com.dto.CartItemResponseDto;
import com.yodha.e_com.dto.CartRequestDto;
import com.yodha.e_com.dto.CartResponseDto;
import com.yodha.e_com.services.CartService;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> createCart(@Valid @RequestBody CartRequestDto cartRequestDto ) {
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartResponseDto savedCart= cartService.createCart(Email,cartRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart Created", savedCart));
    }

    @GetMapping
    public   ResponseEntity<ApiResponse<CartResponseDto>> getCart() {
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartResponseDto cart= cartService.getCartByEmail(Email);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart Fetched", cart));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> clearCart() {
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.clearCartByEmail(Email);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart Cleared", null));
    }

    @PatchMapping("/{productId}/{quantity}")
    public ResponseEntity<ApiResponse<CartResponseDto>> updateCartItem(@PathVariable ObjectId productId, @PathVariable int quantity  ) {
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartResponseDto updatedCart = cartService.updateCart(Email, productId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart Item Updated", updatedCart));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> removeCartItem(@PathVariable ObjectId productId ) {
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartResponseDto removedItem = cartService.removeItemFromCart(Email, productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart Item Removed", removedItem));
    }


}
