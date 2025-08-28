package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    List<Order> findByEmail(String email);
}
