package com.compass.reinan.api_ecommerce.domain.dto.product;

import com.compass.reinan.api_ecommerce.domain.entity.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        Integer quantityInStock,
        BigDecimal price,
        Category category,
        Boolean active
) {
}
