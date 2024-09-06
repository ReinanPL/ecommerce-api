package com.compass.reinan.api_ecommerce.domain.dto.user.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record PasswordTokenResponse(
        String token,
        @JsonProperty(value = "expiration_date")
        String expirationDate
) {
}
