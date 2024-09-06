package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.CategoryController;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponse;
import com.compass.reinan.api_ecommerce.domain.dto.category.CreateCategoryRequest;
import com.compass.reinan.api_ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(createCategoryRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.findCategoryById(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/{id}/active")
    public ResponseEntity<CategoryResponse> activeCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.activeCategory(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/{id}/name")
    public ResponseEntity<CategoryResponse> updateCategoryName(@PathVariable Long id, @RequestBody @Valid CreateCategoryRequest requestDto) {
        return ResponseEntity.ok().body(categoryService.modifyCategoryName(id, requestDto.name()));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }
}
