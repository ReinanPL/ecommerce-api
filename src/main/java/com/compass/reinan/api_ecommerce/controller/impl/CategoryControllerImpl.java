package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.CategoryController;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
import com.compass.reinan.api_ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    @PostMapping
    public ResponseEntity<CategoryResponseDto> saveCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryRequestDto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.findCategoryById(id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/active")
    public ResponseEntity<CategoryResponseDto> activeCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.activeCategory(id));
    }

    @Override
    @PatchMapping("/{id}/name")
    public ResponseEntity<CategoryResponseDto> modifyCategoryName(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto requestDto) {
        return ResponseEntity.ok().body(categoryService.modifyCategoryName(id, requestDto.name()));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }
}
