package com.compass.reinan.api_ecommerce.domain.dto.sale;

import java.math.BigDecimal;

public record ItemSaleResponse(
        Long productId,
        Integer quantity,
        BigDecimal price
) {
}
