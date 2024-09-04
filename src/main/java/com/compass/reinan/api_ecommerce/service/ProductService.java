package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.UpdateProductRequest;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponse save(ProductRequest productRequest);
    ProductResponse findById(Long id);
    void deleteById(Long id);
    ProductResponse update(Long id, UpdateProductRequest productRequest);
    ProductResponse activeProduct(Long id);
    PageableResponse<ProductResponse> findAll(int page, int size);
    PageableResponse<ProductResponse> findAllProductActives(Long categoryId, int page, int size, BigDecimal min, BigDecimal max);
}
