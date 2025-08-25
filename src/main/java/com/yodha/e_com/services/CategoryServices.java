 package com.yodha.e_com.services;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.mapper.CategoryMapper;
import com.yodha.e_com.repository.ProductCategoryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


 @Service
public class CategoryServices {

    @Autowired
    private ProductCategoryRepo categoryRepo;


    @Autowired
    private CategoryMapper categoryMapper;

    public ProductCategory createCategory(CategoryRequest request) {
        ProductCategory Category = categoryMapper.toEntity(request);
        return categoryRepo.save(Category);
    }

    public void deleteCategory(String name) {
        ProductCategory Category = categoryRepo.findByName(name).orElseThrow(
                () -> new RuntimeException("Category not found with name: " + name)
        );

        categoryRepo.deleteById(Category.getId());
    }

    public List<ProductCategory> findAll() {
        return categoryRepo.findAll();
    }

    public ProductCategory updateCategory(CategoryRequest request ,String categoryName) {
        ProductCategory existingCategory = categoryRepo.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        existingCategory.setName(request.getName());
        return categoryRepo.save(existingCategory);
    }

}
