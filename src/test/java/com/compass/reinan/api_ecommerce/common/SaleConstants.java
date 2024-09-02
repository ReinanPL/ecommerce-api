package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.sale.*;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSale;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSalePK;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.compass.reinan.api_ecommerce.common.ProductConstants.PRODUCT;
import static com.compass.reinan.api_ecommerce.common.UserConstants.USER_EXISTING;

public class SaleConstants {
    public static final ItemSaleRequest ITEM_SALE_REQUEST = new ItemSaleRequest(1L, 10);
    public static final SaleRequest SALE_REQUEST = new SaleRequest("52624127003", Collections.singletonList(ITEM_SALE_REQUEST));
    public static final SaleResponse SALE_RESPONSE = new SaleResponse(1L, Instant.now(), "52624127003", Status.PROCESSING.toString(), Collections.singletonList(new ItemSaleResponse(1L, 10, BigDecimal.valueOf(12.0))), BigDecimal.valueOf(12.0));
    public static final Sale SALE = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, Collections.emptySet());


    public static final Sale SALE_CANCELED = new Sale(1L, Instant.now(), USER_EXISTING, Status.CANCELED, Collections.emptySet());
    public static final SaleResponse SALE_RESPONSE_CANCELED = new SaleResponse(1L, Instant.now(), "52624127003", Status.CANCELED.toString(), Collections.singletonList(new ItemSaleResponse(1L, 10, BigDecimal.valueOf(12.0))), BigDecimal.valueOf(12.0));



//Sale_withItemList
    static List<ItemSaleRequest> items2ListRequest = new ArrayList<>(List.of(
            new ItemSaleRequest(1L, 2),
            new ItemSaleRequest(2L, 2)));
    public static final SaleRequest SALE_REQUEST_TWO_ITEM = new SaleRequest("52624127003", items2ListRequest);

    static Set<ItemSale> items2 = new HashSet<>(List.of(
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(10.2)),
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(12.2)
            )));
    public static final Sale SALE_TWO_ITEM = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, items2);

    static List<ItemSaleResponse> items2Response = new ArrayList<>(List.of(
            new ItemSaleResponse(1L, 2, BigDecimal.valueOf(10.2)),
            new ItemSaleResponse(2L, 2, BigDecimal.valueOf(10.2))));
    public static final SaleResponse SALE_RESPONSE_TWO_ITEM = new SaleResponse(1L, Instant.now(), "52624127003", Status.PROCESSING.toString(), items2Response, BigDecimal.valueOf(10.0));


    //UpdateItem
    static List<ItemSaleRequest> itemUpdateRequest = new ArrayList<>(List.of(
            new ItemSaleRequest(1L, 2),
            new ItemSaleRequest(1L, 4),
            new ItemSaleRequest(3L, 3)));
    public static final UpdateItemSale UPDATE_ITEM_SALE = new UpdateItemSale(itemUpdateRequest);

    static Set<ItemSale> items3 = new HashSet<>(List.of(
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(10.2)),
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 4, BigDecimal.valueOf(12.2)),
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 3, BigDecimal.valueOf(12.2)
            )));
    public static final Sale SALE_THREE_ITEM = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, items3);

    static List<ItemSaleResponse> updateItemSaleResponse = new ArrayList<>(List.of(
            new ItemSaleResponse(1L, 2, BigDecimal.valueOf(10.2)),
            new ItemSaleResponse(2L, 4, BigDecimal.valueOf(12.2)),
            new ItemSaleResponse(3L, 3, BigDecimal.valueOf(12.2))));
    public static final SaleResponse SALE_RESPONSE_THREE_ITEM = new SaleResponse(1L, Instant.now(), "52624127003", Status.PROCESSING.toString(), updateItemSaleResponse, BigDecimal.valueOf(10.0));

    //patchItem
    //delete
    static List<Long> removeItem = new ArrayList<>(List.of(1L));
    public static final UpdatePatchItemSale UPDATE_PATCH_ITEM_SALE = new UpdatePatchItemSale(null, removeItem);

    static Set<ItemSale> items = new HashSet<>(List.of(
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(10.2))
    ));
    public static final Sale SALE_ONE_ITEM = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, items);

    static List<ItemSaleResponse> itemsList = new ArrayList<>(List.of(
            new ItemSaleResponse(1L, 2, BigDecimal.valueOf(10.2))));
    public static final SaleResponse SALE_RESPONSE_ONE_ITEM = new SaleResponse(1L, Instant.now(), "52624127003", Status.PROCESSING.toString(), itemsList, BigDecimal.valueOf(10.0));
}


