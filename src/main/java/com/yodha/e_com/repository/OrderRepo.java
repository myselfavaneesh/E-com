package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepo extends MongoRepository<Order, ObjectId> {
}
