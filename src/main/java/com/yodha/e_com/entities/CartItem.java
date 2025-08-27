package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;


import java.util.List;

@Data
public class CartItem {
    private ObjectId productId;
    private String productName;
    private int quantity;
    private double price; //SnapShot of price at the time of adding to cart
    private String image;
}
