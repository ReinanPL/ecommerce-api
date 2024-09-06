package com.compass.reinan.api_ecommerce.domain.dto.page;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageableResponse<T>(
        List<T> content ,
        @JsonProperty(value = "page_number")
        int pageNumber,
        @JsonProperty(value = "page_size")
        int pageSize,
        @JsonProperty(value = "total_elements")
        long totalElements,
        @JsonProperty(value = "total_pages")
        int totalPages
) {
}
