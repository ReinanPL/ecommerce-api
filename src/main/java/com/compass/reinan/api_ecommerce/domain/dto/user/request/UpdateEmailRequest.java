package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email format is invalid", regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        @JsonProperty(value = "new_email")
        String newEmail
) {
}
