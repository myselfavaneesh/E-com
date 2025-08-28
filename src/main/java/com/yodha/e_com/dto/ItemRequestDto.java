package com.yodha.e_com.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ItemRequestDto {
    @NotNull(message = "ProductId is required")
    private ObjectId productId;

    @Min(value = 1, message = "Quantity should be at least 1")
    private int quantity;
}