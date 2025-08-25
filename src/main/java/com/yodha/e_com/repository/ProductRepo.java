package com.yodha.e_com.repository;

import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.entities.Products;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface ProductRepo extends MongoRepository <Products, ObjectId> {
    List<Products> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Products> findByCategory(ProductCategory category);
}
