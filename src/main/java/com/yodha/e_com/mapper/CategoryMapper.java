package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.dto.ProductRequest;
import com.yodha.e_com.dto.ProductResponse;
import com.yodha.e_com.entities.ProductCategory;
import com.yodha.e_com.entities.Products;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    ProductCategory toEntity(CategoryRequest dto);

}
