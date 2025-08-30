package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Product;
import com.yodha.e_com.entities.ProductCategory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepo extends MongoRepository<Product, ObjectId> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByCategory(ProductCategory category);
}
