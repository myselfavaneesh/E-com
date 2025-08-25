package com.yodha.e_com.controller;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.services.CategoryServices;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServices categoryServices;

    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        ProductCategory createdCategory = categoryServices.createCategory(categoryRequest);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Categories created successfully",
                createdCategory.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable String name) {
        categoryServices.deleteCategory(name);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Category deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<ProductCategory>>> getAllCategories() {
        Iterable<ProductCategory> categories = categoryServices.findAll();
        ApiResponse<Iterable<ProductCategory>> response = new ApiResponse<>(
                true,
                "Categories fetched successfully",
                categories
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryName}")
    public ResponseEntity<ApiResponse<ProductCategory>> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable String categoryName) {
        ProductCategory updatedCategory = categoryServices.updateCategory(categoryRequest,categoryName);
        ApiResponse<ProductCategory> response = new ApiResponse<>(
                true,
                "Category updated successfully",
                updatedCategory
        );
        return ResponseEntity.ok(response);
    }
}
