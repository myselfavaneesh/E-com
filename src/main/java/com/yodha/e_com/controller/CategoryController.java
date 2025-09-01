package com.yodha.e_com.controller;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.dto.CategoryResponseDTO;
import com.yodha.e_com.services.CategoryServices;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServices categoryServices;

    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        CategoryResponseDTO createdCategory = categoryServices.createCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,
                "Categories created successfully",
                createdCategory
        ));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable String name) {
        categoryServices.deleteCategory(name);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Category deleted successfully",
                null
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryServices.findAll();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Categories fetched successfully",
                categories
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable String id) {
        CategoryResponseDTO updatedCategory = categoryServices.updateCategory(categoryRequest, new ObjectId(id));
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Category updated successfully",
                updatedCategory
        ));
    }
}
