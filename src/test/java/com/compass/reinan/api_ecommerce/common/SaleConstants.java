package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.ItemSaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSale;
import com.compass.reinan.api_ecommerce.domain.entity.ItemSalePK;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;
import com.compass.reinan.api_ecommerce.util.DateUtils;
import org.h2.mvstore.DataUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.compass.reinan.api_ecommerce.common.ProductConstants.PRODUCT;
import static com.compass.reinan.api_ecommerce.common.UserConstants.USER_EXISTING;

public class SaleConstants {
    public static final CreateItemSaleRequest ITEM_SALE_REQUEST = new CreateItemSaleRequest(1L, 10);
    public static final CreateSaleRequest SALE_REQUEST = new CreateSaleRequest("52624127003", Collections.singletonList(ITEM_SALE_REQUEST));
    public static final SaleResponse SALE_RESPONSE = new SaleResponse(1L, DateUtils.formatToISO8601(Instant.now()), "52624127003", Status.PROCESSING.toString(), Collections.singletonList(new ItemSaleResponse(1L, 10,BigDecimal.valueOf(12.0))), BigDecimal.valueOf(12.0));
    public static final Sale SALE = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, Collections.emptySet());


    public static final Sale SALE_CANCELED = new Sale(1L, Instant.now(), USER_EXISTING, Status.CANCELED, Collections.emptySet());
    public static final SaleResponse SALE_RESPONSE_CANCELED = new SaleResponse(1L, DateUtils.formatToISO8601(Instant.now()), "52624127003", Status.CANCELED.toString(), Collections.singletonList(new ItemSaleResponse(1L, 10, BigDecimal.valueOf(12.0))), BigDecimal.valueOf(12.0));



//Sale_withItemList
    static List<CreateItemSaleRequest> items2ListRequest = new ArrayList<>(List.of(
            new CreateItemSaleRequest(1L, 2),
            new CreateItemSaleRequest(2L, 2)));
    public static final CreateSaleRequest SALE_REQUEST_TWO_ITEM = new CreateSaleRequest("52624127003", items2ListRequest);

    static Set<ItemSale> items2 = new HashSet<>(List.of(
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(10.2)),
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(12.2)
            )));
    public static final Sale SALE_TWO_ITEM = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, items2);

    static List<ItemSaleResponse> items2Response = new ArrayList<>(List.of(
            new ItemSaleResponse(1L, 2, BigDecimal.valueOf(10.2)),
            new ItemSaleResponse(2L, 2, BigDecimal.valueOf(10.2))));
    public static final SaleResponse SALE_RESPONSE_TWO_ITEM = new SaleResponse(1L, DateUtils.formatToISO8601(Instant.now()), "52624127003", Status.PROCESSING.toString(), items2Response, BigDecimal.valueOf(10.0));


    //UpdateItem
    static List<CreateItemSaleRequest> itemUpdateRequest = new ArrayList<>(List.of(
            new CreateItemSaleRequest(1L, 2),
            new CreateItemSaleRequest(1L, 4),
            new CreateItemSaleRequest(3L, 3)));
    public static final UpdateItemSaleRequest UPDATE_ITEM_SALE = new UpdateItemSaleRequest(itemUpdateRequest);

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
    public static final SaleResponse SALE_RESPONSE_THREE_ITEM = new SaleResponse(1L, DateUtils.formatToISO8601(Instant.now()), "52624127003", Status.PROCESSING.toString(), updateItemSaleResponse, BigDecimal.valueOf(10.0));

    //patchItem
    //delete
    static List<Long> removeItem = new ArrayList<>(List.of(1L));
    public static final UpdatePatchItemSaleRequest UPDATE_PATCH_ITEM_SALE = new UpdatePatchItemSaleRequest(null, removeItem);

    static Set<ItemSale> items = new HashSet<>(List.of(
            new ItemSale(new ItemSalePK(SALE, PRODUCT), 2, BigDecimal.valueOf(10.2))
    ));
    public static final Sale SALE_ONE_ITEM = new Sale(1L, Instant.now(), USER_EXISTING, Status.PROCESSING, items);

    static List<ItemSaleResponse> itemsList = new ArrayList<>(List.of(
            new ItemSaleResponse(1L, 2, BigDecimal.valueOf(10.2))));
    public static final SaleResponse SALE_RESPONSE_ONE_ITEM = new SaleResponse(1L, DateUtils.formatToISO8601(Instant.now()), "52624127003", Status.PROCESSING.toString(), itemsList, BigDecimal.valueOf(10.0));


    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final Pageable PAGEABLE = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    public static final Page<Sale> SALE_PAGE = new PageImpl<>(Collections.singletonList(SALE), PAGEABLE, 1);

    public static final Page<Sale> EMPTY_SALE_PAGE = new PageImpl<>(Collections.emptyList(), PAGEABLE, 1);

    public static final PageableResponse<SaleResponse> PAGE_SALE_RESPONSE = new PageableResponse<>(
            Collections.singletonList(SALE_RESPONSE), 0, 10, 1,1
    );

    public static final PageableResponse<SaleResponse> PAGE_SALE_EMPTY_RESPONSE = new PageableResponse<>(
            Collections.emptyList(), 0, 10, 0,0
    );

}








