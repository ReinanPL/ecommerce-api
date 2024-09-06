package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.CreateProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductActiveResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.response.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.request.UpdateProductRequest;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.service.ProductService;
import com.compass.reinan.api_ecommerce.service.mapper.PageableMapper;
import com.compass.reinan.api_ecommerce.service.mapper.ProductMapper;
import com.compass.reinan.api_ecommerce.util.EntityUtils;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final PageableMapper pageableMapper;

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id", allEntries = true)
    public ProductResponse save(CreateProductRequest createProductRequest) {
        Optional.of(productRepository.existsByName(createProductRequest.name()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Product: '%s' already exists", createProductRequest.name())));

        var product = mapper.toEntity(createProductRequest);

        var category = EntityUtils.getEntityOrThrow(createProductRequest.categoryId(), Category.class, categoryRepository);
        Optional.of(category.getActive())
                        .filter(active -> active)
                        .orElseThrow(() -> new EntityActiveStatusException("Category is not active"));
        product.setCategory(category);

        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductActiveResponse findById(Long id) {
        return productRepository.findProductByIdAndActive(id)
                .map(mapper::toActiveResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product not found with id: %s", id)));
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id", allEntries = true)
    public void deleteById(Long id) {
        var product = EntityUtils.getEntityOrThrow(id, Product.class, productRepository);

        Consumer<Product> deleteAction = productRepository::delete;
        Consumer<Product> inactiveAction = this::inactiveProduct;

        Optional.of(product)
                .map(items -> items.getItems().isEmpty() ? deleteAction : inactiveAction)
                .ifPresent(action -> action.accept(product));
    }

    @Override
    @Transactional
    @CachePut(value = "products", key = "#id")
    public ProductResponse update(Long id, UpdateProductRequest productRequest) {
        var product = EntityUtils.getEntityOrThrow(id, Product.class, productRepository);

        Optional.of(productRepository.existsByName(productRequest.name()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Product: '%s' already exists", productRequest.name())));

        var productUpdate = mapper.updateToEntity(productRequest);

        product.setName(productUpdate.getName() !=null ? productUpdate.getName() : product.getName());
        product.setQuantityInStock(productUpdate.getQuantityInStock() != null ? productUpdate.getQuantityInStock() : product.getQuantityInStock());
        product.setPrice(productUpdate.getPrice() != null ? productUpdate.getPrice() : product.getPrice());
        product.setCategory(productRequest.categoryId() != null ? EntityUtils.getEntityOrThrow(productRequest.categoryId(), Category.class, categoryRepository) : product.getCategory());

        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    @CachePut(value = "products", key = "#id")
    public ProductResponse activeProduct(Long id) {
        var product = EntityUtils.getEntityOrThrow(id, Product.class, productRepository);

        Optional.of(product.getActive())
                .filter(active -> !active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' already active ", id)));

        product.setActive(true);
        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("products")
    public PageableResponse<ProductResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pageableMapper.toProductResponse(productRepository.findAllProducts(pageable));
    }

    @Transactional(readOnly = true)
    @Cacheable("products")
    public PageableResponse<ProductActiveResponse> findAllProductActives(Long categoryId, int page, int size, BigDecimal min, BigDecimal max) {
        Pageable pageable = PageRequest.of(page, size);
        return pageableMapper.toProductActiveResponse(productRepository.findProductsActiveByFilters(categoryId, min, max, pageable));
    }

    private void inactiveProduct(Product product) {
        Optional.of(product.getActive())
                .filter(active -> active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' already inactive ", product.getId())));

        product.setActive(false);
        productRepository.save(product);
    }
}
