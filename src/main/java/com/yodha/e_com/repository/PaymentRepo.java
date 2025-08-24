package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepo extends MongoRepository<Payment, ObjectId> {
}
