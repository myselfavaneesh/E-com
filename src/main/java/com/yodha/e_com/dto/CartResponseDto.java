package com.yodha.e_com.dto;


import com.yodha.e_com.entities.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private String email;
    private double totalPrice;
    private List<CartItem> cartItems;
}
