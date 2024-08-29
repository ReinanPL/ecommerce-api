package com.compass.reinan.api_ecommerce.domain.dto.product;

import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
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
        Long category_id
) {
}
