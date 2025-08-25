package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.ProductRequest;
import com.yodha.e_com.dto.ProductResponse;
import com.yodha.e_com.entities.Products;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "category", ignore = true)
    Products toProduct(ProductRequest dto);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Products product);

    @Mapping(source = "categoryName", target = "category", ignore = true)
    void updateProductFromRequest(ProductRequest request, @MappingTarget Products product);
}
