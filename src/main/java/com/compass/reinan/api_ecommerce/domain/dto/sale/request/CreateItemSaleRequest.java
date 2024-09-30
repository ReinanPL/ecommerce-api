package com.compass.reinan.api_ecommerce.domain.dto.sale.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateItemSaleRequest(
        @NotNull(message = "Product Id cannot be null")
        @JsonProperty(value = "product_id")
        Long productId,
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Item quantity must be greater than 0")
        Integer quantity
) {
}
