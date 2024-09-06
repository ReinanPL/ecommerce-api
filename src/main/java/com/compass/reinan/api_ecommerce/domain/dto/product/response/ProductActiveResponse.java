package com.compass.reinan.api_ecommerce.domain.dto.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductActiveResponse(
        Long id,
        String name,
        @JsonProperty(value = "quantity_in_stock")
        Integer quantityInStock,
        BigDecimal price,
        String category
) {
}
