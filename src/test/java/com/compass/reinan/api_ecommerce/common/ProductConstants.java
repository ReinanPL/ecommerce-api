package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.product.ProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSale;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSalePK;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.compass.reinan.api_ecommerce.common.CategoryConstants.CATEGORY;
import static com.compass.reinan.api_ecommerce.common.UserConstants.USER_EXISTING;

public class ProductConstants {

    public static final ProductResponse PRODUCT_RESPONSE = new ProductResponse(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), CATEGORY, true);
    public static final ProductRequest PRODUCT_REQUEST = new ProductRequest("Test Product", 10 , BigDecimal.valueOf(12.0), 1L);


    public static final Product PRODUCT = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.emptySet());

    public static final ItemSale itemSale = new ItemSale(new ItemSalePK(null , null), 10, BigDecimal.valueOf(12.0));

    public static final Product PRODUCT_ACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_INACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), false, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_ALREADY_INACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), false, null, Collections.singleton(itemSale));
    public static final Product PRODUCT_ALREADY_ACTIVE = new Product(1L, "Test Product", 10 , BigDecimal.valueOf(12.0), true, null, Collections.singleton(itemSale));
}
