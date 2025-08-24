package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
@Data
public class Order {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private int totalAmount;
    private enum status {
        PENDING,
        SHIPPED,
        DELIVERED
    };
}
