package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.sale.ItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.ItemSaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;

import com.compass.reinan.api_ecommerce.domain.entity.Sale;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = SaleItemMapper.class)
public interface SaleMapper {

    @Mapping(target = "dateSale", expression = "java(java.time.Instant.now())")
    @Mapping(target = "status", constant = "PROCESSING")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Sale toEntity(SaleRequest saleRequest);

    @Mapping(target = "userCpf", source = "user.cpf")
    @Mapping(target = "totalValue", expression = "java(calculateTotalValue(items))")
    SaleResponse toResponse(Sale Sale);

    default BigDecimal calculateTotalValue(List<ItemSaleResponse> items) {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}

