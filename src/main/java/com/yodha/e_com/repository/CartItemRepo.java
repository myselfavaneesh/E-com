package com.yodha.e_com.repository;

import com.yodha.e_com.entities.CartItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartItemRepo extends MongoRepository<CartItem, ObjectId> {
}
