package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserCreateRequest(
        @Size(min = 11, max = 11, message = "User cpf must be 11 characters")
        @CPF(message = "Invalid cpf")
        String cpf,
        @NotBlank(message = "First name cannot be empty")
        @Size(min = 5, max = 50, message = "Minimum size of 5 and maximum of 50")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        @Size(min = 5, max = 50, message = "Minimum size of 5 and maximum of 50")
        String lastName,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email format is invalid", regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
        String email,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String password,
        @NotBlank
        @Size(min = 6, message = "Minimum of 6 characters")
        String confirmPassword,
        @Valid
        AddressRequest address
) {
}
