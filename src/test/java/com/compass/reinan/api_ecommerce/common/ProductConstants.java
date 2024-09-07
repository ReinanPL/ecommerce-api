package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.CreateProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSale;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSalePK;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;

import static com.compass.reinan.api_ecommerce.common.CategoryConstants.CATEGORY;

public class ProductConstants {

    public static final ProductResponse PRODUCT_RESPONSE = new ProductResponse(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), CATEGORY, true);
    public static final CreateProductRequest PRODUCT_REQUEST = new CreateProductRequest("Test Product", 10 , BigDecimal.valueOf(12.0), 1L);

    public static final ProductActiveResponse PRODUCT_ACTIVE_RESPONSE = new ProductActiveResponse(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), "Category test");


    public static final Product PRODUCT = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.emptySet());

    public static final ItemSale itemSale = new ItemSale(new ItemSalePK(null , null), 10, BigDecimal.valueOf(12.0));

    public static final Product PRODUCT_ACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_INACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), false, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_ALREADY_INACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), false, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_ALREADY_ACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.singleton(itemSale));


    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final Pageable PAGEABLE = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    public static final Page<Product> EMPTY_PRODUCT_PAGE = new PageImpl<>(Collections.emptyList(), PAGEABLE, 0);
    public static final Page<Product> PRODUCT_PAGE = new PageImpl<>(Collections.singletonList(PRODUCT), PAGEABLE, 1);

    public static final PageableResponse<ProductResponse> PAGE_PRODUCT_RESPONSE = new PageableResponse<>(
            Collections.singletonList(PRODUCT_RESPONSE), 0, 10, 1,1
    );

    public static final PageableResponse<ProductActiveResponse> PAGE_PRODUCT_CLIENT_RESPONSE = new PageableResponse<>(
            Collections.singletonList(PRODUCT_ACTIVE_RESPONSE), 0, 10, 1,1
    );


    public static final PageableResponse<ProductResponse> PAGE_PRODUCT_EMPTY_RESPONSE = new PageableResponse<>(
            Collections.emptyList(), 0, 10, 0,0
    );
    public static final PageableResponse<ProductActiveResponse> PAGE_PRODUCT_ACTIVE_EMPTY_RESPONSE = new PageableResponse<>(
            Collections.emptyList(), 0, 10, 0,0
    );
}
