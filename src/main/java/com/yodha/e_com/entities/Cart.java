package com.yodha.e_com.entities;


import lombok.Data;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "carts")
@Data
public class Cart {
    @Id
    private ObjectId id;
    @DBRef
    private User user;
    private List<CartItem> items;
}
