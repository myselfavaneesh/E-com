package com.yodha.e_com.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Category extends MongoRepository<Category, ObjectId> {
}
