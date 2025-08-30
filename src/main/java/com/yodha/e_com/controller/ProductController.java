package com.yodha.e_com.controller;

import com.yodha.e_com.dto.ProductRequest;
import com.yodha.e_com.dto.ProductResponse;
import com.yodha.e_com.services.ProductServices;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServices productService;

    public ProductController(ProductServices productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse createdProduct = productService.createProduct(productRequest);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product created successfully",
                createdProduct
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(@RequestParam(required = false) String categoryName,
                                                                          @RequestParam(required = false) Double minPrice,
                                                                          @RequestParam(required = false) Double maxPrice) {

        List<ProductResponse> product = productService.getAllProducts(categoryName, minPrice, maxPrice);

        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(
                true,
                "Product fetched successfully",
                product
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable ObjectId id) {
        ProductResponse product = productService.getProductById(id);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product fetched successfully",
                product
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable ObjectId id) {
        productService.deleteProduct(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable ObjectId id,
            @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                true,
                "Product updated successfully",
                updatedProduct
        );

        return ResponseEntity.ok(response);
    }

}
