package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;

public interface SaleService {
    SaleResponse save(CreateSaleRequest createSaleRequest);
    SaleResponse findById(Long id);
    void deleteById(Long id);
    SaleResponse cancelSale(Long id);
    SaleResponse completeSale(Long id);
    SaleResponse updateSale(Long id, UpdateItemSaleRequest updateSaleRequest);
    SaleResponse patchSale(Long id, UpdatePatchItemSaleRequest patchSaleRequest);
    PageableResponse<SaleResponse> findAll(int page, int size);
}
