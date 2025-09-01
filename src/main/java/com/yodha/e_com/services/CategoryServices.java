package com.yodha.e_com.services;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.dto.CategoryResponseDTO;
import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.mapper.CategoryMapper;
import com.yodha.e_com.repository.ProductCategoryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServices {

    @Autowired
    private ProductCategoryRepo categoryRepo;


    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryResponseDTO createCategory(CategoryRequest request) {
        Optional<ProductCategory> existingCategory = categoryRepo.findByName(request.getName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists with name: " + existingCategory.get().getName());
        }
        return categoryMapper.toDto(categoryRepo.save(categoryMapper.toEntity(request)));
    }

    public void deleteCategory(String name) {
        ProductCategory Category = categoryRepo.findByName(name).orElseThrow(
                () -> new RuntimeException("Category not found with name: " + name)
        );

        categoryRepo.deleteById(Category.getId());
    }

    public List<CategoryResponseDTO> findAll() {
        List<ProductCategory> categories = categoryRepo.findAll();
        List<CategoryResponseDTO> responseDTO = new ArrayList<>();
        categories.forEach(category -> responseDTO.add(categoryMapper.toDto(category)));
        return responseDTO;
    }

    public CategoryResponseDTO updateCategory(CategoryRequest request, ObjectId id) {
        ProductCategory existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        existingCategory.setName(request.getName());
        return categoryMapper.toDto(categoryRepo.save(existingCategory));
    }

}
