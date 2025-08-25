package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category")
@Data
public class ProductCategory {
    @Id
    private ObjectId id;
    private String name;
}
