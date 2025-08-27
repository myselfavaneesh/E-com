package com.yodha.e_com.dto;

import lombok.Data;
import org.bson.types.ObjectId;


@Data
public class CartItemResponseDto {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String image;
}
