 package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private ObjectId id;
    private String email;
    private List<CartItem> cartItems;
    private double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
