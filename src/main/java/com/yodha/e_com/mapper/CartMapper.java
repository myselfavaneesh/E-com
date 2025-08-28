package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.ItemRequestDto;
import com.yodha.e_com.dto.CartResponseDto;
import com.yodha.e_com.entities.Cart;
import com.yodha.e_com.entities.CartItem;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CartMapper {

    CartItem toEntity(ItemRequestDto dto);


    CartResponseDto toCartResponseDto(Cart cart);

    @AfterMapping
    default void calculateTotalPrice(@MappingTarget Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

}
