package com.compass.reinan.api_ecommerce.domain.dto.sale;

import java.math.BigDecimal;
import java.util.List;

public record SaleResponse(
        Long id,
        String dateSale,
        String userCpf,
        String status,
        List<ItemSaleResponse> items,
        BigDecimal totalValue

) {
}
