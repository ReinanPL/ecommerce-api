package com.compass.reinan.api_ecommerce.domain.dto.product.response;

import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({"id", "name", "quantity_in_stock", "price", "category", "active"})
public record ProductResponse(
        Long id,
        String name,
        @JsonProperty(value = "quantity_in_stock")
        Integer quantityInStock,
        BigDecimal price,
        Category category,
        Boolean active
) {
}
