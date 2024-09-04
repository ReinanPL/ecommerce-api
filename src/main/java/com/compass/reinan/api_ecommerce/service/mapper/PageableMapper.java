package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {SaleItemMapper.class, SaleMapper.class})
public interface PageableMapper {

    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "number", target = "pageNumber")
    PageableResponse<SaleResponse> toSaleResponse(Page<Sale> page);

    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "number", target = "pageNumber")
    PageableResponse<ProductResponse> toProductResponse(Page<Product> page);
}