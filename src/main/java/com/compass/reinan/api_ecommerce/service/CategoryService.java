package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponse;
import com.compass.reinan.api_ecommerce.domain.dto.category.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse saveCategory(CreateCategoryRequest categoryRequest);
    CategoryResponse findCategoryById(Long id);
    void deleteCategory(Long id);
    CategoryResponse activeCategory(Long id);
    CategoryResponse modifyCategoryName(Long id, String name);
    List<CategoryResponse> getAllCategories();
}
