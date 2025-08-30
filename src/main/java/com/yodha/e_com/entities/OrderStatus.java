package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "order_status")
public class OrderStatus {
    @Id
    private ObjectId Id;
    private String trackingNumber;
    private ObjectId orderId;
    private String status;
    private LocalDateTime updatedAt;
}
