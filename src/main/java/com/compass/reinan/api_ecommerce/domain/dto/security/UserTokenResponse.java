package com.compass.reinan.api_ecommerce.domain.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserTokenResponse(
        @JsonProperty(value = "access_token")
        String accessToken,
        @JsonProperty(value = "expires_in")
        Long ExpiresIn
) {
}
