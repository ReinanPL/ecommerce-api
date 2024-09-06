package com.compass.reinan.api_ecommerce.domain.dto.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        @Size(min = 3, message = "Product name must be at least 3 characters")
        String name,
        @NotNull
        @Min(value = 0, message = "Quantity in stock must be a positive number")
        @JsonProperty(value = "quantity_in_stock")
        Integer quantityInStock,
        @NotNull
        @Min(value = 0, message = "Price must be a positive number")
        BigDecimal price,
        @NotNull
        @JsonProperty(value = "category_id")
        Long categoryId
) {
}
