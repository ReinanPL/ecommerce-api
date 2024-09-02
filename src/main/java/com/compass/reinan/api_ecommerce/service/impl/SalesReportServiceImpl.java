package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.repository.SaleRepository;
import com.compass.reinan.api_ecommerce.service.SalesReportService;
import com.compass.reinan.api_ecommerce.service.mapper.SaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final SaleRepository saleRepository;
    private final SaleMapper mapper;

    public List<SaleResponse> findSalesByCpfAndDate(String cpf, int day, int month, int year) {
        return saleRepository.findSalesByCpfAndDate(cpf, day, month, year).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> findSalesByCpfAndMonth(String cpf, int month, int year) {
        return saleRepository.findSalesByCpfAndMonth(cpf, month, year).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> findSalesByCpfAndCurrentWeek(String cpf) {
        Instant startOfWeek = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Instant endOfWeek = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .plusDays(6)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return saleRepository.findSalesByCpfAndCurrentWeek(cpf, startOfWeek, endOfWeek).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}