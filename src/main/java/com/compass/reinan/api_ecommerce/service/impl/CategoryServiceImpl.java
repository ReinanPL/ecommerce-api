package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponse;
import com.compass.reinan.api_ecommerce.domain.dto.category.CreateCategoryRequest;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;
import com.compass.reinan.api_ecommerce.service.CategoryService;
import com.compass.reinan.api_ecommerce.service.mapper.CategoryMapper;
import com.compass.reinan.api_ecommerce.util.EntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryResponse saveCategory(CreateCategoryRequest categoryRequest){
        Optional.of(categoryRepository.existsByName(categoryRequest.name()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Category '%s' already exists", categoryRequest.name())));

        return mapper.toResponse(categoryRepository.save(mapper.toEntity(categoryRequest)));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findCategoryById(Long id){
        var category = EntityUtils.getEntityOrThrow(id, Category.class, categoryRepository);
        return mapper.toResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id){
        var category = EntityUtils.getEntityOrThrow(id, Category.class, categoryRepository);

        Consumer<Category> deleteAction = categoryRepository::delete;
        Consumer<Category> inactiveAction = this::inactiveCategory;

        Optional.of(category)
                .map(cat -> cat.getProducts().isEmpty() ? deleteAction : inactiveAction)
                .ifPresent(action -> action.accept(category));
    }

    @Override
    @Transactional
    public CategoryResponse activeCategory(Long id){
        var category = EntityUtils.getEntityOrThrow(id, Category.class, categoryRepository);

        Optional.of(category.getActive())
                .filter(active -> !active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' is already active ", id)));

        category.setActive(true);
        return mapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse modifyCategoryName(Long id, String name) {
        var category = EntityUtils.getEntityOrThrow(id, Category.class, categoryRepository);

        Optional.of(categoryRepository.existsByName(name))
                .filter(exist -> !exist)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Category '%s' already exists", name)));

        category.setName(name);
        return mapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private void inactiveCategory(Category category) {
        Optional.of(category.getActive())
                .filter(active -> active)
                .orElseThrow(() -> new EntityActiveStatusException(String.format("Category Id: '%s' is already inactive ", category.getId())));

        category.setActive(false);
        categoryRepository.save(category);
    }
}
