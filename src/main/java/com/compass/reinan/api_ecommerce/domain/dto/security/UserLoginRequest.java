package com.compass.reinan.api_ecommerce.domain.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserLoginRequest(
        @Size(min = 11, max = 11, message = "User cpf must be 11 characters")
        @CPF(message = "Invalid cpf")
        @JsonProperty(value = "user_cpf")
        String cpf,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String password
) {
}
