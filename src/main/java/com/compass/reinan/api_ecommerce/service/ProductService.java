package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.CreateProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.UpdateProductRequest;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponse save(CreateProductRequest createProductRequest);
    ProductActiveResponse findById(Long id);
    void deleteById(Long id);
    ProductResponse update(Long id, UpdateProductRequest productRequest);
    ProductResponse activeProduct(Long id);
    PageableResponse<ProductResponse> findAll(int page, int size);
    PageableResponse<ProductActiveResponse> findAllProductActives(Long categoryId, int page, int size, BigDecimal min, BigDecimal max);
}
