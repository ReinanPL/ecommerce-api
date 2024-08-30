package com.compass.reinan.api_ecommerce.domain;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;

import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;

import com.compass.reinan.api_ecommerce.service.impl.CategoryServiceImpl;
import com.compass.reinan.api_ecommerce.service.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compass.reinan.api_ecommerce.common.CategoryConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void saveCategory_WithValidData_ShouldSaveAndReturnResponseDto() {
        when(categoryRepository.existsByName(CATEGORY_REQUEST_DTO.name())).thenReturn(false);
        when(mapper.toEntity(CATEGORY_REQUEST_DTO)).thenReturn(CATEGORY);
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY);
        when(mapper.toResponseDto(CATEGORY)).thenReturn(CATEGORY_RESPONSE_DTO);

        CategoryResponseDto actualResponseDto = categoryService.saveCategory(CATEGORY_REQUEST_DTO);

        assertThat(actualResponseDto).isEqualTo(CATEGORY_RESPONSE_DTO);
        verify(categoryRepository, times(1)).save(eq(CATEGORY));
        verify(categoryRepository, times(1)).existsByName(eq(CATEGORY_REQUEST_DTO.name()));
    }

    @Test
    public void saveCategory_WithNameAlreadyRegistered_ShouldThrowDataUniqueViolationException() {
        when(mapper.toEntity(CATEGORY_REQUEST_DTO)).thenReturn(CATEGORY);
        when(categoryRepository.save(CATEGORY)).thenThrow(new DataUniqueViolationException("Category already exists"));

        assertThrows(DataUniqueViolationException.class, () -> categoryService.saveCategory(CATEGORY_REQUEST_DTO));
        verify(categoryRepository, times(1)).save(CATEGORY);
    }

    @Test
    public void findCategoryById_WithExistingId_ShouldReturnResponseDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY));
        when(mapper.toResponseDto(CATEGORY)).thenReturn(CATEGORY_RESPONSE_DTO);

        CategoryResponseDto actualResponseDto = categoryService.findCategoryById(1L);

        assertThat(actualResponseDto).isEqualTo(CATEGORY_RESPONSE_DTO);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void findCategoryById_WithNonExistingId_ShouldThrowEntityNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(1L));

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteCategory_WhenCategoryExistsAndHasNoProducts_ShouldDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(CATEGORY);
    }

    @Test
    public void deleteCategory_WhenCategoryExistsAndHasProducts_ShouldInactiveCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY_ACTIVE));
        when(categoryRepository.save(CATEGORY_ACTIVE)).thenReturn(CATEGORY_INACTIVE);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, never()).delete(CATEGORY_ACTIVE);
        verify(categoryRepository, times(1)).save(CATEGORY_ACTIVE);
    }

    @Test
    public void inactiveCategory_WithCategoryAlreadyInactive_ShouldThrowCategoryActiveException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY_ALREADY_INACTIVE));

        assertThrows(EntityActiveStatusException.class, () -> categoryService.deleteCategory(1L));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void activateCategory_WithInactiveCategory_ShouldReturnActivatedCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY_INACTIVE));
        when(categoryRepository.save(CATEGORY_INACTIVE)).thenReturn(CATEGORY_ACTIVE);
        when(mapper.toResponseDto(CATEGORY_ACTIVE)).thenReturn(CATEGORY_RESPONSE_DTO);

        CategoryResponseDto actualResponseDtoList = categoryService.activeCategory(1L);

        verify(categoryRepository, times(1)).save(CATEGORY_INACTIVE);
        verify(categoryRepository, times(1)).findById(1L);
        assertThat(CATEGORY_RESPONSE_DTO.active()).isEqualTo(true);
    }

    @Test
    public void activateCategory_WithAlreadyActiveCategory_ShouldThrowCategoryActiveException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY_ALREADY_ACTIVE));

        assertThrows(EntityActiveStatusException.class, () -> categoryService.activeCategory(1L));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void updateCategoryName_WithValidNewName_ShouldReturnUpdatedCategory() {
        String newCategoryName = "New Category Name";
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY));
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY_NEW_NAME);
        when(mapper.toResponseDto(CATEGORY_NEW_NAME)).thenReturn(CATEGORY_RESPONSE_NEW_NAME_DTO);

        CategoryResponseDto actualResponseDto = categoryService.modifyCategoryName(1L, newCategoryName);

        assertEquals(CATEGORY_RESPONSE_NEW_NAME_DTO, actualResponseDto);
        verify(categoryRepository, times(1)).save(CATEGORY);
    }

    @Test
    public void getAllCategories_WithCategories_ShouldReturnListOfCategoryDtos() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(CATEGORY));
        when(mapper.toResponseDto(CATEGORY)).thenReturn(CATEGORY_RESPONSE_DTO);

        List<CategoryResponseDto> actualResponseDtoList = categoryService.getAllCategories();

        assertEquals(1, actualResponseDtoList.size());
        assertEquals(CATEGORY_RESPONSE_DTO, actualResponseDtoList.get(0));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void getAllCategories_WithNoCategories_ShouldReturnEmptyList() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<CategoryResponseDto> actualResponseDtoList = categoryService.getAllCategories();

        assertEquals(0, actualResponseDtoList.size());
        verify(categoryRepository, times(1)).findAll();
    }


}



