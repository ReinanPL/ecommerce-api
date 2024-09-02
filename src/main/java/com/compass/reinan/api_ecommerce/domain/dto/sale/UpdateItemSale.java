package com.compass.reinan.api_ecommerce.domain.dto.sale;

import java.util.List;

public record UpdateItemSale(
        List<ItemSaleRequest> items
) {
}
