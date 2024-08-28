package com.compass.reinan.api_ecommerce.domain.dto.user.response;

import com.compass.reinan.api_ecommerce.domain.entity.Address;

public record UserResponse(
        String cpf,
        String email,
        String firstName,
        String lastName,
        Address address,
        String role

) {
}
