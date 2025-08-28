package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class OrderItem {
    private ObjectId productId;
    private String productName;
    private int quantity;
    private double price;
}
