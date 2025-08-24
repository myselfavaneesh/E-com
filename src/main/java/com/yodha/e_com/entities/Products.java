package com.yodha.e_com.entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
@Data
public class Products {

    @Id
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private int price;
    private int stock;
    @DBRef
    private Category category;
    private List<String> images;
}


