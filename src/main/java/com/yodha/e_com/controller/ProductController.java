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
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    "Invalid product ID format",
                    null
            ));
        }

        ProductResponse product = productService.getProductById(new ObjectId(id));

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Product fetched successfully",
                product
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    "Invalid product ID format",
                    null
            ));
        }

        productService.deleteProduct(new ObjectId(id));

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest productRequest) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    "Invalid product ID format",
                    null
            ));
        }

        ProductResponse updatedProduct = productService.updateProduct(new ObjectId(id), productRequest);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Product updated successfully",
                updatedProduct
        ));
    }

}
