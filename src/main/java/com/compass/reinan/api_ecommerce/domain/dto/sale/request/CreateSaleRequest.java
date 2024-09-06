package com.compass.reinan.api_ecommerce.domain.dto.sale.request;

import com.compass.reinan.api_ecommerce.validator.NotEmptyList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CreateSaleRequest(
        @NotBlank
        @Size(min = 11, max = 11, message = "User cpf must be 11 characters")
        @CPF(message = "Invalid cpf")
        String user_cpf,
        @Valid
        @NotEmptyList
        List<CreateItemSaleRequest> items
) {
}
