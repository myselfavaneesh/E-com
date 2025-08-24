package com.yodha.e_com.repository;

import com.yodha.e_com.entities.OrderItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepo extends MongoRepository<OrderItem, ObjectId> {
}
