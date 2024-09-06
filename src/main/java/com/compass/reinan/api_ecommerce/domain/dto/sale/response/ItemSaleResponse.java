package com.compass.reinan.api_ecommerce.domain.dto.sale.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ItemSaleResponse(
        @JsonProperty(value = "product_id")
        Long productId,
        Integer quantity,
        BigDecimal price
) {
}
