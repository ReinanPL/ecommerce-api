package com.compass.reinan.api_ecommerce.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @Size(min = 3, message = "Product name must be at least 3 characters")
        String name,
        @Min(value = 0, message = "Quantity in stock must be a positive number")
        @JsonProperty(value = "quantity_in_stock")
        Integer quantityInStock,
        @Min(value = 0, message = "Price must be a positive number")
        BigDecimal price

) {
}
