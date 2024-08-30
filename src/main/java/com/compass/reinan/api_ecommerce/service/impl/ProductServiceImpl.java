package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.product.ProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.UpdateProductRequest;
import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.service.ProductService;
import com.compass.reinan.api_ecommerce.service.mapper.ProductMapper;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    @Transactional
    public ProductResponse save(ProductRequest productRequest) {
        Optional.of(productRepository.existsByName(productRequest.name()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Product: '%s' already exists", productRequest.name())));
        return mapper.toResponse(productRepository.save(mapper.toEntity(productRequest,categoryRepository)));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: '%s' not found", id)));
        return mapper.toResponse(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: '%s' not found", id)));
        Consumer<Product> deleteAction = productRepository::delete;
        Consumer<Product> inactiveAction = this::inactiveProduct;
        Optional.of(product)
                .map(cat -> cat.getItems().isEmpty() ? deleteAction : inactiveAction)
                .ifPresent(action -> action.accept(product));
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest productRequest) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: '%s' not found", id)));
        Optional.of(productRepository.existsByName(productRequest.name()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Product: '%s' already exists", productRequest.name())));

        var productUpdate = mapper.updateToEntity(productRequest);
        product.setName(!StringUtils.isBlank(productRequest.name()) ? productUpdate.getName() : product.getName());
        product.setQuantityInStock(productUpdate.getQuantityInStock() != null ? productUpdate.getQuantityInStock() : product.getQuantityInStock());
        product.setPrice(productUpdate.getPrice() != null ? productUpdate.getPrice() : product.getPrice());
        product.setCategory(productUpdate.getCategory()!= null? productUpdate.getCategory() : product.getCategory());
        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse activeProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: '%s' not found", id)));
        Optional.of(product.getActive())
                .filter(active -> !active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' already active ", id)));
        product.setActive(true);
        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private void inactiveProduct(Product product) {
        Optional.of(product.getActive())
                .filter(active -> active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' already inactive ", product.getId())));
        product.setActive(false);
        productRepository.save(product);
    }
}
