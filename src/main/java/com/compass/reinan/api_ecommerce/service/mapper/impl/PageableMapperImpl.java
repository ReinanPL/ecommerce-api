package com.compass.reinan.api_ecommerce.service.mapper.impl;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import com.compass.reinan.api_ecommerce.service.mapper.PageableMapper;
import com.compass.reinan.api_ecommerce.service.mapper.ProductMapper;
import com.compass.reinan.api_ecommerce.service.mapper.SaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class PageableMapperImpl implements PageableMapper {

    private final SaleMapper saleMapper;
    private final ProductMapper productMapper;

    @Override
    public PageableResponse<SaleResponse> toSaleResponse(Page<Sale> page) {
        if ( page == null ) {
            return null;
        }

        int pageSize = 0;
        int pageNumber = 0;
        List<SaleResponse> content = Collections.emptyList();
        long totalElements = 0L;
        int totalPages = 0;

        pageSize = page.getSize();
        pageNumber = page.getNumber();
        if ( page.hasContent() ) {
            content = saleListToSaleResponseList( page.getContent() );
        }
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();

        return new PageableResponse<SaleResponse>( content, pageNumber, pageSize, totalElements, totalPages );
    }

    @Override
    public PageableResponse<ProductResponse> toProductResponse(Page<Product> page) {
        if ( page == null ) {
            return null;
        }

        int pageSize = 0;
        int pageNumber = 0;
        List<ProductResponse> content = Collections.emptyList();
        long totalElements = 0L;
        int totalPages = 0;

        pageSize = page.getSize();
        pageNumber = page.getNumber();
        if ( page.hasContent() ) {
            content = productListToProductResponseList( page.getContent() );
        }
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();

        return new PageableResponse<ProductResponse>( content, pageNumber, pageSize, totalElements, totalPages );
    }

    @Override
    public PageableResponse<ProductActiveResponse> toProductActiveResponse(Page<Product> page) {
        if ( page == null ) {
            return null;
        }

        int pageSize = 0;
        int pageNumber = 0;
        List<ProductActiveResponse> content = Collections.emptyList();
        long totalElements = 0L;
        int totalPages = 0;

        pageSize = page.getSize();
        pageNumber = page.getNumber();
        if ( page.hasContent() ) {
            content = productListToProductActiveResponseList( page.getContent() );
        }
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();

        return new PageableResponse<ProductActiveResponse>( content, pageNumber, pageSize, totalElements, totalPages );
    }

    protected List<SaleResponse> saleListToSaleResponseList(List<Sale> list) {
        if ( list == null ) {
            return null;
        }

        List<SaleResponse> list1 = new ArrayList<SaleResponse>( list.size() );
        for ( Sale sale : list ) {
            list1.add( saleMapper.toResponse( sale ) );
        }

        return list1;
    }

    protected List<ProductResponse> productListToProductResponseList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductResponse> list1 = new ArrayList<ProductResponse>( list.size() );
        for ( Product product : list ) {
            list1.add( productMapper.toResponse( product ) );
        }

        return list1;
    }

    protected List<ProductActiveResponse> productListToProductActiveResponseList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductActiveResponse> list1 = new ArrayList<ProductActiveResponse>( list.size() );
        for ( Product product : list ) {
            list1.add( productMapper.toActiveResponse( product ) );
        }

        return list1;
    }
}
