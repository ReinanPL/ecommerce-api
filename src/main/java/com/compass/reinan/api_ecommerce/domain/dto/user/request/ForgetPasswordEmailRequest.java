package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordEmailRequest(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email format is invalid", regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        String email
) {
}
