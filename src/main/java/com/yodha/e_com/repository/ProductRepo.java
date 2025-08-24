package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Products;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository <Products, ObjectId> {
}
