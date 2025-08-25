package com.yodha.e_com.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class ProductResponse {
    private String name;
    private String description;
    private double price;
    private int stock;
    private String categoryName;
    private List<String> images;
}

