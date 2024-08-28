package com.compass.reinan.api_ecommerce.domain.dto.user.response;


public record PasswordTokenResponse(
        String token,
        String expirationDate
) {
}
