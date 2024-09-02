package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;

import java.util.List;

public interface SalesReportService {
    List<SaleResponse> findSalesByCpfAndDate(String cpf, int day, int month, int year);
    List<SaleResponse> findSalesByCpfAndMonth(String cpf, int month, int year);
    List<SaleResponse> findSalesByCpfAndCurrentWeek(String cpf);
}
