package com.yodha.e_com.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CartRequestDto {
    @NotEmpty(message = "Cart must have at least one item")
    private List<CartItemRequestDto> cartItems;
}
