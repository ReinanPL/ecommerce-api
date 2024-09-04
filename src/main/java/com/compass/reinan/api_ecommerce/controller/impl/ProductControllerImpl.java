package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.ProductController;
import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.UpdateProductRequest;
import com.compass.reinan.api_ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest productRequest) {
        return ResponseEntity.ok().body(productService.update(id, productRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("{id}")
    public ResponseEntity<ProductResponse> activeProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.activeProduct(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<PageableResponse<ProductResponse>> findAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.findAll(page, size));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @GetMapping
    public ResponseEntity<PageableResponse<ProductResponse>> findAllProductsActives(@RequestParam(required = false) Long categoryId,
                                                                                      @RequestParam(required = false) BigDecimal minPrice,
                                                                                      @RequestParam(required = false) BigDecimal maxPrice,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.findAllProductActives(categoryId, page, size, minPrice, maxPrice));

    }
}
