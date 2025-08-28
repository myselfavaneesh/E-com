package com.yodha.e_com.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class OrderResponseDTO {
    private ObjectId id;
    private String status;
    private double totalAmount;
    private String orderDate;
    private String shippingAddress;
    private String paymentId;
    private List<ItemResponseDto> items;
    private String paymentUrl;
}
