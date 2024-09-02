package com.compass.reinan.api_ecommerce.domain.dto.sale;

import jakarta.validation.constraints.NotNull;

public record ItemSaleRequest(
        @NotNull(message = "Product Id cannot be null")
        Long productId,
        @NotNull(message = "Quantity cannot be null")
        Integer quantity
) {
}
