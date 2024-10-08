package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.entity.*;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.InsufficientStockException;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.repository.SaleRepository;
import com.compass.reinan.api_ecommerce.repository.StockReservationRepository;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.SaleService;
import com.compass.reinan.api_ecommerce.service.mapper.PageableMapper;
import com.compass.reinan.api_ecommerce.service.mapper.SaleMapper;
import com.compass.reinan.api_ecommerce.util.EntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockReservationRepository stockReservationRepository;
    private final SaleMapper saleMapper;
    private final PageableMapper pageableMapper;

    @Override
    @Transactional
    @CacheEvict(value = {"sales", "products"}, key = "#id", allEntries = true)
    public SaleResponse save(CreateSaleRequest createSaleRequest) {
        var user = EntityUtils.getEntityOrThrow(createSaleRequest.user_cpf(), User.class, userRepository);

        checkUserAuthorization(createSaleRequest.user_cpf());

        var sale = saleMapper.toEntity(createSaleRequest);
        sale.setUser(user);
        sale.getItems().clear();
        var savedSale = saleRepository.save(sale);

        processItems(savedSale, aggregateItems(createSaleRequest.items()));

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
        Optional.of(sale.getStatus().equals(Status.CANCELED))
                .filter(status -> status)
                .orElseThrow(() -> new EntityActiveStatusException("To delete a sale it needs to be canceled"));

        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    @Caching(put = @CachePut(value = "sales", key = "#id"), evict = @CacheEvict(value = "products", allEntries = true))
    public SaleResponse cancelSale(Long id) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        Optional.of(sale.getStatus().equals(Status.COMPLETED))
                .filter(status -> !status)
                .orElseThrow(() -> new EntityActiveStatusException("It is not possible to cancel a sale that has already been completed"));

        sale.setStatus(Status.CANCELED);
        var deleteReservation = stockReservationRepository.findBySale(sale);
        for (StockReservation reservation : deleteReservation) {
            stockReservationRepository.delete(reservation);
        }
        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    @Caching(put = @CachePut(value = "sales", key = "#id"), evict = @CacheEvict(value = "products", allEntries = true))
    public SaleResponse completeSale(Long id) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        Optional.of(sale.getStatus().equals(Status.PROCESSING))
                .filter(status -> status)
                .orElseThrow(() -> new EntityActiveStatusException("Sale status is already completed"));

        sale.setStatus(Status.COMPLETED);

        confirmOrderPayment(sale);

        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    @Caching(put = @CachePut(value = "sales", key = "#id"), evict = @CacheEvict(value = "products", allEntries = true))
    public SaleResponse updateSale(Long id, UpdateItemSaleRequest updateSaleRequest) {
        var sale = EntityUtils.getEntityOrThrow(id, Sale.class, saleRepository);

        checkUserAuthorization(sale.getUser().getCpf());
        checkIfSaleIsCancelled(sale);

        sale.getItems().clear();
        processItems(sale, aggregateItems(updateSaleRequest.items()));

        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional
    @Caching(put = @CachePut(value = "sales", key = "#id"), evict = @CacheEvict(value = "products", allEntries = true))
    public SaleResponse patchSale(Long id, UpdatePatchItemSaleRequest patchSaleRequest) {
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
        return pageableMapper.toSaleResponse(saleRepository.findAllSales(PageRequest.of(page, size)));
    }

    private List<CreateItemSaleRequest> aggregateItems(List<CreateItemSaleRequest> items) {
        return items.stream()
                .collect(Collectors.groupingBy(
                        CreateItemSaleRequest::productId,
                        Collectors.summingInt(CreateItemSaleRequest::quantity)
                ))
                .entrySet().stream()
                .map(entry -> new CreateItemSaleRequest(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private void processItems(Sale sale, List<CreateItemSaleRequest> itemRequests) {
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
                            existingItem -> updateExistingItem(product, existingItem, itemRequest.quantity(), sale),
                            () -> addNewItemToSale(sale, product, itemRequest.quantity())
                    );
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

            sale.getItems().remove(itemToRemove);
        });
    }

    private void updateExistingItem(Product product, ItemSale existingItem, int newQuantity, Sale sale) {
        int oldQuantity = existingItem.getQuantity();

        if (newQuantity > oldQuantity) {
            validateProductQuantity(product, newQuantity);
        }

        existingItem.setQuantity(newQuantity);
        var deleteStock = stockReservationRepository.findBySale(sale);
        for(StockReservation stockReservation : deleteStock) {
            if(stockReservation.getProduct().equals(product)){
                stockReservationRepository.delete(stockReservation);
            }
        }

        reserveStock(product, existingItem.getQuantity(), sale);
    }

    private void addNewItemToSale(Sale sale, Product product, int quantity) {
        validateProductQuantity(product, quantity);
        sale.getItems().add(new ItemSale(new ItemSalePK(sale, product), quantity, product.getPrice()));
        reserveStock(product, quantity, sale);
    }

    public void reserveStock(Product product, int quantity, Sale order) {
        StockReservation reservation = new StockReservation();
        reservation.setProduct(product);
        reservation.setQuantity(quantity);
        reservation.setSale(order);
        reservation.setCreatedAt(Instant.now());

        stockReservationRepository.save(reservation);
    }

    public void validateProductQuantity(Product product, int quantity) {
        var reservedStock = stockReservationRepository.sumReservedStockByProduct(product);

        var stockAvailable = product.getQuantityInStock() - (reservedStock != null ? reservedStock : 0);

        Optional.of(stockAvailable > 0 && stockAvailable >= quantity)
                .filter(isStock -> isStock)
                .orElseThrow(() -> new InsufficientStockException(String.format("Product Id: '%s' doesn't have enough stock", product.getId())));
    }

    public void confirmOrderPayment(Sale sale) {
        var reservations = stockReservationRepository.findBySale(sale);
        for (StockReservation reservation : reservations) {
            var product = reservation.getProduct();
            product.setQuantityInStock(product.getQuantityInStock() - reservation.getQuantity());
            productRepository.save(product);
            stockReservationRepository.delete(reservation);
        }
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
