package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.*;
import com.compass.reinan.api_ecommerce.domain.entity.*;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.InsufficientStockException;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.repository.SaleRepository;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.SaleService;
import com.compass.reinan.api_ecommerce.service.mapper.PageableMapper;
import com.compass.reinan.api_ecommerce.service.mapper.SaleMapper;
import com.compass.reinan.api_ecommerce.util.EntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleMapper saleMapper;
    private final PageableMapper pageableMapper;

    @Override
    @Transactional
    @CacheEvict(value = "sales", key = "#id", allEntries = true)
    public SaleResponse save(SaleRequest saleRequest) {
        var user = EntityUtils.getEntityOrThrow(saleRequest.user_cpf(), User.class, userRepository);

        checkUserAuthorization(saleRequest.user_cpf());

        var sale = saleMapper.toEntity(saleRequest);
        sale.setUser(user);
        sale.getItems().clear();
        var savedSale = saleRepository.save(sale);

        processItems(savedSale, aggregateItems(saleRequest.items()));

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse findById(Long id) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional
    @CacheEvict(value = "sales", key = "#id", allEntries = true)
    public void deleteById(Long id) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        sale.getItems().forEach(deleteItem -> {
            var product = EntityUtils.getEntityOrThrow(deleteItem.getProduct().getId(), Product.class, productRepository);
            product.setQuantityInStock(product.getQuantityInStock() + deleteItem.getQuantity());
            productRepository.save(product);
        });

        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    @CachePut(value = "sales", key = "#id")
    public SaleResponse cancelSale(Long id) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        sale.setStatus(Status.CANCELED);

        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    @CachePut(value = "sales", key = "#id")
    public SaleResponse updateSale(Long id, UpdateItemSale updateSaleRequest) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        sale.getItems().clear();
        processItems(sale, aggregateItems(updateSaleRequest.items()));

        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    @CachePut(value = "sales", key = "#id")
    public SaleResponse patchSale(Long id, UpdatePatchItemSale patchSaleRequest) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        processItems(sale, patchSaleRequest.updateItems());
        processRemoveItems(sale, patchSaleRequest.removeItems());

        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Cacheable("sales")
    @Transactional(readOnly = true)
    public PageableResponse<SaleResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pageableMapper.toSaleResponse(saleRepository.findAllSales(pageable));
    }

    private List<ItemSaleRequest> aggregateItems(List<ItemSaleRequest> items) {
        return items.stream()
                .collect(Collectors.groupingBy(
                        ItemSaleRequest::productId,
                        Collectors.summingInt(ItemSaleRequest::quantity)
                ))
                .entrySet().stream()
                .map(entry -> new ItemSaleRequest(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private void processItems(Sale sale, List<ItemSaleRequest> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            return;
        }
        itemRequests.forEach(itemRequest -> {
            var product = EntityUtils.getEntityOrThrow(itemRequest.productId(), Product.class, productRepository);
            Optional.of(product.getActive())
                    .filter(active -> active)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Product Id: '%s' is inactive " , product.getId())));

            sale.getItems().stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findFirst()
                    .ifPresentOrElse(
                            existingItem -> updateExistingItem(product, existingItem, itemRequest.quantity()),
                            () -> addNewItemToSale(sale, product, itemRequest.quantity())
                    );

            productRepository.save(product);
        });
    }

    private void processRemoveItems(Sale sale, List<Long> removeItems) {
        if (removeItems == null || removeItems.isEmpty()) {
            return;
        }
        removeItems.forEach(productId -> {
            var product = EntityUtils.getEntityOrThrow(productId, Product.class, productRepository);

            var itemToRemove = sale.getItems().stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findFirst().orElseThrow(() ->new EntityNotFoundException("Item not found in sale with product id: " + productId));

            product.setQuantityInStock(product.getQuantityInStock() + itemToRemove.getQuantity());
            productRepository.save(product);

            sale.getItems().remove(itemToRemove);
        });
    }

    private void updateExistingItem(Product product, ItemSale existingItem, int newQuantity) {
        int oldQuantity = existingItem.getQuantity();

        if (newQuantity > oldQuantity) {
            validateProductQuantity(product, newQuantity);
            product.setQuantityInStock(product.getQuantityInStock() - (newQuantity - oldQuantity));
        } else if (newQuantity < oldQuantity) {
            product.setQuantityInStock(product.getQuantityInStock() + (oldQuantity - newQuantity));
        }

        existingItem.setQuantity(newQuantity);
    }

    private void addNewItemToSale(Sale sale, Product product, int quantity) {
        validateProductQuantity(product, quantity);
        sale.getItems().add(new ItemSale(new ItemSalePK(sale, product), quantity, product.getPrice()));
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    private void validateProductQuantity(Product product, int requestedQuantity) {
        Optional.of(product.getQuantityInStock() < requestedQuantity)
                .filter(stock -> !stock)
                .orElseThrow(() -> new InsufficientStockException(String.format("Product Id: '%s' don't have enough stock " , product.getId())));
    }

    private void checkIfSaleIsCancelled(Sale sale){
        Optional.of(sale.getStatus().equals(Status.CANCELED))
                .filter(canceled -> !canceled)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Sale Id: '%s' is already canceled ", sale.getId())));
    }

    private void checkUserAuthorization(String saleCpf){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional.of(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_ADMIN"))
                        || authentication.getName().equals(saleCpf))
                .filter(equals -> equals)
                .orElseThrow(() -> new AccessDeniedException("User is not authorized"));
    }
}
