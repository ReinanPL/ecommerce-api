package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import com.compass.reinan.api_ecommerce.validator.ValidRole;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateRequest(
        @NotNull
        @ValidRole
        String role
) {
}
