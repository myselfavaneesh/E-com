package com.yodha.e_com.services;

import com.yodha.e_com.dto.ProductRequest;
import com.yodha.e_com.dto.ProductResponse;
import com.yodha.e_com.entities.Product;
import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.exception.ResourceNotFoundException;
import com.yodha.e_com.mapper.ProductMapper;
import com.yodha.e_com.repository.ProductCategoryRepo;
import com.yodha.e_com.repository.ProductRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServices {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductCategoryRepo categoryRepository;

    @Autowired
    private ProductMapper productMapper;


    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        ProductCategory category = categoryRepository.findByName(request.getCategoryName()).
                orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + request.getCategoryName()));
        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        return productMapper.toResponse(savedProduct);
    }


    public List<ProductResponse> getAllProducts(String categoryName, Double minPrice, Double maxPrice) {
        List<Product> products;
        if (categoryName != null) {
            ProductCategory category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

            products = productRepo.findByCategory(category);
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("No products found in category: " + categoryName);
            }
        } else if (minPrice != null && maxPrice != null) {
            products = productRepo.findByPriceBetween(minPrice, maxPrice);
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("No products found in the price range: " + minPrice + " - " + maxPrice);
            }
        } else {
            products = productRepo.findAll();
        }
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : products) {
            responses.add(productMapper.toResponse(product));
        }
        return responses;
    }

    public ProductResponse getProductById(ObjectId id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    public void deleteProduct(ObjectId id) {
        if (!productRepo.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    public ProductResponse updateProduct(ObjectId id, ProductRequest request) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productMapper.updateProductFromRequest(request, existingProduct);

        if (request.getCategoryName() != null) {
            ProductCategory category = categoryRepository.findByName(request.getCategoryName())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + request.getCategoryName()));
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepo.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

}
