package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        @JsonProperty(value = "old_password")
        String oldPassword,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        @JsonProperty(value = "new_password")
        String newPassword,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        @JsonProperty(value = "confirm_password")
        String confirmPassword
) {
}
