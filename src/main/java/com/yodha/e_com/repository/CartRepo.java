package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Cart;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepo extends MongoRepository<Cart, ObjectId> {
}
