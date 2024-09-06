package com.compass.reinan.api_ecommerce.domain.dto.sale.request;

import com.compass.reinan.api_ecommerce.validator.AtLeastOneNotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@AtLeastOneNotEmpty(message = "At least one of the lists must contain elements.")
public record UpdatePatchItemSaleRequest(
        @JsonProperty(value = "update_items")
        List<CreateItemSaleRequest> updateItems,
        @JsonProperty(value = "remove_items")
        List<Long> removeItems
) {
}
