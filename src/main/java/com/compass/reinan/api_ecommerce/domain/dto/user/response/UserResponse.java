package com.compass.reinan.api_ecommerce.domain.dto.user.response;

import com.compass.reinan.api_ecommerce.domain.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"cpf", "email", "first_name", "last_name", "address", "role"})
public record UserResponse(
        String cpf,
        String email,
        @JsonProperty(value = "first_name")
        String firstName,
        @JsonProperty(value = "last_name")
        String lastName,
        Address address,
        String role

) {
}
