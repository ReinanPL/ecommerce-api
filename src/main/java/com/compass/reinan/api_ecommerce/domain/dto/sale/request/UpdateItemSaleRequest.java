package com.compass.reinan.api_ecommerce.domain.dto.sale.request;

import java.util.List;

public record UpdateItemSaleRequest(
        List<CreateItemSaleRequest> items
) {
}
