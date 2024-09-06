package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.SaleController;
import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import com.compass.reinan.api_ecommerce.service.SaleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sales")
public class SaleControllerImpl implements SaleController {

    private final SaleService saleService;

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PostMapping
    public ResponseEntity<SaleResponse> saveSale(@RequestBody @Valid CreateSaleRequest createSaleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(createSaleRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> findSaleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(saleService.findById(id));
    }

    @Override@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleById(@PathVariable Long id) {
        saleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    public ResponseEntity<SaleResponse> cancelSale(@PathVariable Long id) {
        return ResponseEntity.ok().body(saleService.cancelSale(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<SaleResponse> updateSale(@PathVariable Long id, @RequestBody @Valid UpdateItemSaleRequest itemSaleRequest) {
        return ResponseEntity.ok().body(saleService.updateSale(id, itemSaleRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<SaleResponse> patchSale(@PathVariable Long id, @RequestBody @Valid UpdatePatchItemSaleRequest patchItemSale) {
        return ResponseEntity.ok().body(saleService.patchSale(id, patchItemSale));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<PageableResponse<SaleResponse>> findAllSales(@RequestParam(defaultValue = "0")  int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(saleService.findAll(page, size));
    }
}
