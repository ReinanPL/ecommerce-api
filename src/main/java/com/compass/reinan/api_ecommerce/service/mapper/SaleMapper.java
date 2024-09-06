package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.sale.response.ItemSaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import com.compass.reinan.api_ecommerce.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", uses = SaleItemMapper.class)
public interface SaleMapper {

    @Mapping(target = "dateSale", expression = "java(java.time.Instant.now())")
    @Mapping(target = "status", constant = "PROCESSING")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Sale toEntity(CreateSaleRequest createSaleRequest);

    @Mapping(target = "userCpf", source = "user.cpf")
    @Mapping(target = "totalValue", expression = "java(calculateTotalValue(items))")
    SaleResponse toResponse(Sale Sale);

    default BigDecimal calculateTotalValue(List<ItemSaleResponse> items) {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
    default String formatInstantToISO8601(Instant instant) {
        return DateUtils.formatToISO8601(instant);
    }
}

