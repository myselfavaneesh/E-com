package com.yodha.e_com.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusResponseDTO {
    private String trackingNumber;
    private String orderId;
    private String status;
    private LocalDateTime updatedAt;
}
