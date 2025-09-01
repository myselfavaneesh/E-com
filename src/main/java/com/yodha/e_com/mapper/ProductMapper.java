package com.yodha.e_com.mapper;

import com.yodha.e_com.dto.ProductRequest;
import com.yodha.e_com.dto.ProductResponse;
import com.yodha.e_com.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest dto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "id", expression = "java(product.getId() != null ? product.getId().toString() : null)")
    ProductResponse toResponse(Product product);

    @Mapping(source = "categoryName", target = "category", ignore = true)
    void updateProductFromRequest(ProductRequest request, @MappingTarget Product product);
}
