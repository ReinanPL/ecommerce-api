package com.compass.reinan.api_ecommerce.domain.dto.sale.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({"id", "date_sale", "user_cpf", "status", "items", "total_value"})
public record SaleResponse(
        Long id,
        @JsonProperty(value = "date_sale")
        String dateSale,
        @JsonProperty(value = "user_cpf")
        String userCpf,
        String status,
        List<ItemSaleResponse> items,
        @JsonProperty(value = "total_value")
        BigDecimal totalValue

) {
}
