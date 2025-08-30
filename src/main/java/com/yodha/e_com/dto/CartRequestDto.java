package com.yodha.e_com.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CartRequestDto {
    @NotEmpty(message = "Cart must have at least one item")
    private List<ItemRequestDto> cartItems;
}
