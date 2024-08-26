package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDto toResponseDto(Category category);
    Category toEntity(CategoryRequestDto categoryRequest);


}
