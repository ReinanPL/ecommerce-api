package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto saveCategory(CategoryRequestDto categoryRequest);
    CategoryResponseDto findCategoryById(Long id);
    void deleteCategory(Long id);
    CategoryResponseDto activeCategory(Long id);
    CategoryResponseDto modifyCategoryName(Long id, String name);
    List<CategoryResponseDto> getAllCategories();
}
