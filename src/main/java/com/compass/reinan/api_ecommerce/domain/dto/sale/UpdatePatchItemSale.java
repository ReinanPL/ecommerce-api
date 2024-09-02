package com.compass.reinan.api_ecommerce.domain.dto.sale;

import com.compass.reinan.api_ecommerce.validator.AtLeastOneNotEmpty;

import java.util.List;

@AtLeastOneNotEmpty(message = "At least one of the lists must contain elements.")
public record UpdatePatchItemSale(
        List<ItemSaleRequest> updateItems,
        List<Long> removeItems
) {
}
