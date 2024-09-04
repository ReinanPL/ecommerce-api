package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.UpdateItemSale;
import com.compass.reinan.api_ecommerce.domain.dto.sale.UpdatePatchItemSale;

public interface SaleService {
    SaleResponse save(SaleRequest saleRequest);
    SaleResponse findById(Long id);
    void deleteById(Long id);
    SaleResponse cancelSale(Long id);
    SaleResponse updateSale(Long id, UpdateItemSale updateSaleRequest);
    SaleResponse patchSale(Long id, UpdatePatchItemSale patchSaleRequest);
    PageableResponse<SaleResponse> findAll(int page, int size);
}
