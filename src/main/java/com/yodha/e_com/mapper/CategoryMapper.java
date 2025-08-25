package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.entities.ProductCategory;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    ProductCategory toEntity(CategoryRequest dto);

}
