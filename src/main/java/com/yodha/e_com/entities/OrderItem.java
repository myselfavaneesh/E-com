package com.yodha.e_com.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class OrderItem {
    @Id
    private ObjectId id;
    @DBRef
    private Order order;
    @DBRef
    private Products product;
    private int quantity;
    private double price;
}
