package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import com.compass.reinan.api_ecommerce.validator.ValidRole;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(
        @NotNull
        @ValidRole
        String role
) {
}
