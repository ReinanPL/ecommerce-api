package com.compass.reinan.api_ecommerce.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank
        @Size(min = 3, message = "Category name must be at least 3 characters")
        String name
) {
}
