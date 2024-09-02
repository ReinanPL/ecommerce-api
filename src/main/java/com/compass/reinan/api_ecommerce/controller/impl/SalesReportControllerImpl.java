package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.SalesReportController;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.service.SalesReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sales")
public class SalesReportControllerImpl implements SalesReportController {

    private final SalesReportService reportService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @GetMapping("/{cpf}/reports/by-month")
    public ResponseEntity<List<SaleResponse>> getSalesByMonth(
            @PathVariable String cpf,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok().body(reportService.findSalesByCpfAndMonth(cpf, month, year));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @GetMapping("/{cpf}/reports/current-week")
    public ResponseEntity<List<SaleResponse>> getSalesByCurrentWeek(@PathVariable String cpf) {
        return ResponseEntity.ok().body(reportService.findSalesByCpfAndCurrentWeek(cpf));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @GetMapping("/{cpf}/reports/by-date")
    public ResponseEntity<List<SaleResponse>> getSalesByDate(
            @PathVariable String cpf,
            @RequestParam int day,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok().body(reportService.findSalesByCpfAndDate(cpf, day, month, year));
    }
}
