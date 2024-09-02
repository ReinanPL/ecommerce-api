package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.sale.ItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.ItemSaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    @Mapping(target = "productId", source = "product.id")
    ItemSaleResponse toResponseDto(ItemSale itemSale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    ItemSale toEntity(ItemSaleRequest itemSaleRequest);
}
