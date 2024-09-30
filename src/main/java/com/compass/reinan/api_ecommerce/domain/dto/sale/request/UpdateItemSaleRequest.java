package com.compass.reinan.api_ecommerce.domain.dto.sale.request;

import com.compass.reinan.api_ecommerce.validator.NotEmptyList;
import jakarta.validation.Valid;

import java.util.List;

public record UpdateItemSaleRequest(
        @Valid
        @NotEmptyList
        List<CreateItemSaleRequest> items
) {
}
