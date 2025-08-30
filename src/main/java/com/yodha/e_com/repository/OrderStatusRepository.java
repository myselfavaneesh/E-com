package com.yodha.e_com.repository;

import com.yodha.e_com.entities.OrderStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderStatusRepository extends MongoRepository<OrderStatus, ObjectId> {
    Optional<OrderStatus> findByTrackingNumber(String trackingNumber);
}
