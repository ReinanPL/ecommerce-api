package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgetPasswordRequest(
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String newPassword,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String confirmPassword
) {
}
