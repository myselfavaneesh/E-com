package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
@Data
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private double price;
    private int stock;
    @DBRef
    private ProductCategory category;
    private List<String> images;
}

