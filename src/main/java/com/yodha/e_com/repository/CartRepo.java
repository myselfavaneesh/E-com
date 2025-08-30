package com.yodha.e_com.repository;

import com.yodha.e_com.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepo extends MongoRepository<Cart, String> {
    Optional<Cart> findByEmail(String email);
}
