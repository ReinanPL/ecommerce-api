package com.compass.reinan.api_ecommerce.domain.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @NotBlank(message = "Cep cannot be empty")
        @Size(min = 8, max = 8)
        String cep,
        @NotBlank(message = "City cannot be empty")
        @Size(min = 3, max = 20, message = "Minimum size of 3 and maximum of 20")
        String city,
        @NotBlank(message = "State cannot be empty")
        @Size(min = 2, max = 2, message = "Use the abbreviation for your state: 'SP'")
        String state,
        @NotBlank(message = "Street cannot be empty")
        @Size(min = 5, max = 100, message = "Minimum size of 5 and maximum of 100")
        String street,
        @NotBlank(message = "Number cannot be empty")
        @Size(min = 1, max = 10, message = "Minimum size of 1 and maximum of 10")
        String number,
        @NotBlank(message = "Description cannot be empty")
        @Size(min = 5, max = 200, message = "Minimum size of 5 and maximum of 200")
        String complement
) {
}
