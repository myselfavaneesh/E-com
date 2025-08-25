package com.yodha.e_com.repository;

import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.entities.Products;
import com.yodha.e_com.entities.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends MongoRepository <Products, ObjectId> {

    @Query("{ 'id': ?0 }")
    @Update("{ '$set': { 'name': ?1, 'description': ?2 ,'price': ?3} }")
    void updateProduct(String id, String newName, String newEmail);
    List<Products> findByCategory_Name(String categoryName);
    List<Products> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Products> findByCategory(ProductCategory category);
}
