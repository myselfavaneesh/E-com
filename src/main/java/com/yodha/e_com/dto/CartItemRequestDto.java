package com.yodha.e_com.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class CartItemRequestDto {
    @NotNull(message = "ProductId is required")
    private ObjectId productId;

    @Min(value = 1, message = "Quantity should be at least 1")
    private int quantity;
}