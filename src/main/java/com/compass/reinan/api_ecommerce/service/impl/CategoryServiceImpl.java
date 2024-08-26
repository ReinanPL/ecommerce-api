package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
import com.compass.reinan.api_ecommerce.domain.entity.Category;
import com.compass.reinan.api_ecommerce.exception.CategoryActiveException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;
import com.compass.reinan.api_ecommerce.service.CategoryService;
import com.compass.reinan.api_ecommerce.service.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public CategoryResponseDto saveCategory(CategoryRequestDto categoryRequest){
        try{
            return mapper.toResponseDto(categoryRepository.save(mapper.toEntity(categoryRequest)));
        } catch (RuntimeException e){
            throw new DataUniqueViolationException(String.format("Category '%s' already exists", categoryRequest.name()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto findCategoryById(Long id){
        return categoryRepository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category Id: '%s' not found ", id)));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id){
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category Id: '%s' not found ", id)));

        Consumer<Category> deleteAction = categoryRepository::delete;
        Consumer<Category> inactiveAction = this::inactiveCategory;
        Optional.of(category)
                .map(cat -> cat.getProducts().isEmpty() ? deleteAction : inactiveAction)
                .ifPresent(action -> action.accept(category));
    }

    @Override
    @Transactional
    public CategoryResponseDto activeCategory(Long id){
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category Id: '%s' not found ", id)));
        Optional.of(category.getActive())
                .filter(active -> !active)
                .orElseThrow(() -> new CategoryActiveException(String.format("Category Id: '%s' is already active ", id)));
        category.setActive(true);
        return mapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponseDto modifyCategoryName(Long id, String name) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category Id: '%s' not found ", id)));
        Optional.of(categoryRepository.existsByName(name))
                .filter(exist -> !exist)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Category '%s' already exists", name)));
        category.setName(name);
        return mapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private void inactiveCategory(Category category) {
        Optional.of(category.getActive())
                .filter(active -> active)
                .orElseThrow(() -> new CategoryActiveException(String.format("Category Id: '%s' is already inactive ", category.getId())));
        category.setActive(false);
        categoryRepository.save(category);
    }

}
