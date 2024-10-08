package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponse;
import com.compass.reinan.api_ecommerce.domain.dto.category.CreateCategoryRequest;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.domain.entity.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class CategoryConstants {

    public static final CategoryResponse CATEGORY_RESPONSE_DTO = new CategoryResponse(1L, "Test Category", true);
    public static final CreateCategoryRequest CATEGORY_REQUEST_DTO = new CreateCategoryRequest("Test Category");
    public static final CategoryResponse CATEGORY_RESPONSE_NEW_NAME_DTO = new CategoryResponse(1L,"New Test Category", true);

    public static final Category CATEGORY = new Category(1L, "Test Category", true ,Collections.emptyList());
    public static final Category CATEGORY_NEW_NAME = new Category(1L, "New Test Category", true ,Collections.emptyList());


    public static final Category CATEGORY_ACTIVE = new Category(1L, "Test Category", true , List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
    public static final Category CATEGORY_INACTIVE = new Category(1L, "Test Category", false, List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), false, null, null)));
    public static final Category CATEGORY_ALREADY_INACTIVE = new Category(1L, "Test Category", false, List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), false, null, null)));
    public static final Category CATEGORY_ALREADY_ACTIVE = new Category(1L, "Test Category", true , List.of(new Product(1L, "Test Product", 10, BigDecimal.valueOf(10.1), true, null, null)));
}
