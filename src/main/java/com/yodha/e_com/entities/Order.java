package com.yodha.e_com.entities;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private ObjectId id;
    private String email;
    private List<OrderItem> items;
    private double totalAmount;
    private Status status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String shippingAddress;
    private ObjectId paymentId;

    public enum Status {
        PENDING, SHIPPED, DELIVERED, CONFIRMED, FAILED,CANCELLED
    }
}