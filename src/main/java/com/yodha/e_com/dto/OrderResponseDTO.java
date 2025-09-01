package com.yodha.e_com.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    private String id;
    private String status;
    private double totalAmount;
    private String orderDate;
    private String shippingAddress;
    private String paymentId;
    private List<ItemResponseDto> items;
    private String paymentUrl;
    private String trackingNumber;
}
