package com.compass.reinan.api_ecommerce.service.mapper.impl;

import com.compass.reinan.api_ecommerce.domain.dto.product.request.CreateProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.UpdateProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.repository.StockReservationRepository;
import com.compass.reinan.api_ecommerce.service.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class ProductMapperImpl implements ProductMapper {

    private final StockReservationRepository stockReservationRepository;

    @Override
    public Product toEntity(CreateProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( request.name() );
        product.setQuantityInStock( request.quantityInStock() );
        product.setPrice( request.price() );

        return product;
    }

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Integer quantityInStock = null;
        BigDecimal price = null;
        Category category = null;
        Boolean active = null;

        id = product.getId();
        name = product.getName();
        quantityInStock = product.getQuantityInStock();
        price = product.getPrice();
        category = product.getCategory();
        active = product.getActive();

        return new ProductResponse( id, name, quantityInStock, price, category, active );
    }

    @Override
    public ProductActiveResponse toActiveResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        String category = null;
        Long id = null;
        String name = null;
        Integer quantityInStock = null;
        BigDecimal price = null;

        category = productCategoryName( product );
        id = product.getId();
        name = product.getName();

        Integer reservedStock = stockReservationRepository.sumReservedStockByProduct(product);
        quantityInStock = product.getQuantityInStock() - (reservedStock != null ? reservedStock : 0);

        price = product.getPrice();

        return new ProductActiveResponse( id, name, quantityInStock, price, category );
    }

    @Override
    public Product updateToEntity(UpdateProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( request.name() );
        product.setQuantityInStock( request.quantityInStock() );
        product.setPrice( request.price() );

        return product;
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }
}
