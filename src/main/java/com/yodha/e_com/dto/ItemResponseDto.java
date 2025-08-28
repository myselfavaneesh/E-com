package com.yodha.e_com.dto;

import lombok.Data;


@Data
public class ItemResponseDto {
    private String productName;
    private int quantity;
    private double price;
    private String image;
}
