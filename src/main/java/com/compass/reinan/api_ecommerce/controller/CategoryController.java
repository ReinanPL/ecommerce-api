package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryController {

    ResponseEntity<CategoryResponseDto> saveCategory(CategoryRequestDto categoryRequestDto);
    ResponseEntity<CategoryResponseDto> getCategoryById(Long id);
    ResponseEntity<Void> deleteCategory(Long id);
    ResponseEntity<CategoryResponseDto> activeCategory(Long id);
    ResponseEntity<CategoryResponseDto> modifyCategoryName(Long id, CategoryRequestDto requestDto);
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();

}
