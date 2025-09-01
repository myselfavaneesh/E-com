package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.CategoryRequest;
import com.yodha.e_com.dto.CategoryResponseDTO;
import com.yodha.e_com.entities.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    ProductCategory toEntity(CategoryRequest dto);

    @Mapping(target = "id", expression = "java(entity.getId() != null ? entity.getId().toString() : null)")
    CategoryResponseDTO toDto(ProductCategory entity);

}
