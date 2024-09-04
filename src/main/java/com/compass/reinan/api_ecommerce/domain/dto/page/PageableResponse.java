package com.compass.reinan.api_ecommerce.domain.dto.page;

import java.util.List;

public record PageableResponse<T>(
        List<T> content ,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
