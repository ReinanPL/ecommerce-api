package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.domain.entity.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class CategoryConstants {

    public static final CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L, "Test Category", true);
    public static final CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category");
    public static final CategoryResponseDto categoryResponseNewNameDto = new CategoryResponseDto(1L,"New Test Category", true);

    public static final Category category = new Category(1L, "Test Category", true ,Collections.emptyList());
    public static final Category categoryNewName = new Category(1L, "New Test Category", true ,Collections.emptyList());


    public static final Category categoryActive = new Category(1L, "Test Category", true , List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
    public static final Category categoryInactive = new Category(1L, "Test Category", false, List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
    public static final Category categoryAlreadyInactive = new Category(1L, "Test Category", false, List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
    public static final Category categoryAlreadyActive = new Category(1L, "Test Category", true , List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
}
