package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import org.springframework.data.domain.Page;

public interface PageableMapper {
    PageableResponse<SaleResponse> toSaleResponse(Page<Sale> page);
    PageableResponse<ProductResponse> toProductResponse(Page<Product> page);
    PageableResponse<ProductActiveResponse> toProductActiveResponse(Page<Product> page);
}
