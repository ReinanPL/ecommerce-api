package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateUserRequest(
        @NotBlank
        @Size(min = 11, max = 11, message = "User cpf must be 11 characters")
        @CPF(message = "Invalid cpf")
        String cpf,
        @NotBlank(message = "First name cannot be empty")
        @Size(min = 3, max = 50, message = "Minimum size of 5 and maximum of 50")
        @JsonProperty(value = "first_name")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        @Size(min = 3, max = 50, message = "Minimum size of 5 and maximum of 50")
        @JsonProperty(value = "last_name")
        String lastName,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email format is invalid", regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        String email,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String password,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        @JsonProperty(value = "confirm_password")
        String confirmPassword,
        @Valid
        UpdateAddressRequest address
) {
}
