package com.yodha.e_com.dto;


import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private double totalPrice;
    private List<ItemResponseDto> cartItems;
}
