package com.yodha.e_com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "shippingAddress is mandatory")
    @Size(min = 30, max = 255, message = "shippingAddress can have at most 255 characters")
    private String shippingAddress;
    @NotEmpty(message = "items cannot be empty")
    private List<ItemRequestDto> items;
    @NotBlank(message = "paymentMethod is mandatory")
    private String paymentMethod;
}
