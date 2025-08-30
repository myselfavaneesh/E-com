package com.yodha.e_com.repository;


import com.yodha.e_com.entities.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface UserRepo extends MongoRepository<Users, ObjectId> {
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'email': ?1 } }")
    void updateUserEmail(String oldEmail, String newEmail);

    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'name': ?1, 'email': ?2 } }")
    void updateUserNameAndEmail(String oldEmail, String name, String newEmail);


    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'name': ?1} }")
    void updateUserNameAndPhone(String email, String name);

    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'password': ?1 } }")
    void updatePassword(String email, String hashedPassword);
}
