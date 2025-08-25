package com.yodha.e_com.repository;

import com.yodha.e_com.entities.ProductCategory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductCategoryRepo extends MongoRepository<ProductCategory, ObjectId> {
    Optional<ProductCategory> findByName(String name);
}
