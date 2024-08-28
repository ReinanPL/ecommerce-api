package com.compass.reinan.api_ecommerce.domain.dto.security;

public record UserTokenResponse(
        String accessToken,
        Long ExpiresIn
) {
}
