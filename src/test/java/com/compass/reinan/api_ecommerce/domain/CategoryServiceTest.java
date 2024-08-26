package com.compass.reinan.api_ecommerce.domain;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;

import com.compass.reinan.api_ecommerce.exception.CategoryActiveException;
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
        when(mapper.toEntity(categoryRequestDto)).thenReturn(category);
        when(mapper.toResponseDto(category)).thenReturn(categoryResponseDto);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto actualResponseDto = categoryService.saveCategory(categoryRequestDto);

        assertThat(actualResponseDto).isEqualTo(categoryResponseDto);
        verify(categoryRepository, times(1)).save(eq(category));
    }

    @Test
    public void saveCategory_WithNameAlreadyRegistered_ShouldThrowDataUniqueViolationException() {
        when(mapper.toEntity(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenThrow(new DataUniqueViolationException("Category already exists"));

        assertThrows(DataUniqueViolationException.class, () -> categoryService.saveCategory(categoryRequestDto));
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void findCategoryById_WithExistingId_ShouldReturnResponseDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toResponseDto(category)).thenReturn(categoryResponseDto);

        CategoryResponseDto actualResponseDto = categoryService.findCategoryById(1L);

        assertThat(actualResponseDto).isEqualTo(categoryResponseDto);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void findCategoryById_WithNonExistingId_ShouldThrowEntityNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(1L));


        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteCategory_WhenCategoryExistsAndHasNoProducts_ShouldDeleteCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategory_WhenCategoryExistsAndHasProducts_ShouldInactiveCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryActive));
        when(categoryRepository.save(categoryActive)).thenReturn(categoryInactive);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, never()).delete(categoryActive);
        verify(categoryRepository, times(1)).save(categoryActive);
    }

    @Test
    public void inactiveCategory_WithCategoryAlreadyInactive_ShouldThrowCategoryActiveException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryAlreadyInactive));

        assertThrows(CategoryActiveException.class, () -> categoryService.deleteCategory(1L));

        verify(categoryRepository, never()).save(any()); // No save call expected  //nao funciona
    }

    @Test
    public void activateCategory_WithInactiveCategory_ShouldReturnActivatedCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryInactive));
        when(categoryRepository.save(categoryInactive)).thenReturn(categoryActive);
        when(mapper.toResponseDto(categoryActive)).thenReturn(categoryResponseDto);

        CategoryResponseDto actualResponseDtoList = categoryService.activeCategory(1L);

        verify(categoryRepository, times(1)).save(categoryInactive);
        verify(categoryRepository, times(1)).findById(1L);
        assertThat(categoryResponseDto.active()).isEqualTo(true);
    }

    @Test
    public void activateCategory_WithAlreadyActiveCategory_ShouldThrowCategoryActiveException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryAlreadyActive));

        assertThrows(CategoryActiveException.class, () -> categoryService.activeCategory(1L));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void modifyCategoryName_WithValidNewName_ShouldReturnUpdatedCategory() {
        String newCategoryName = "New Category Name";
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(categoryNewName);
        when(mapper.toResponseDto(categoryNewName)).thenReturn(categoryResponseNewNameDto);

        CategoryResponseDto actualResponseDto = categoryService.modifyCategoryName(1L, newCategoryName);

        assertEquals(categoryResponseNewNameDto, actualResponseDto);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void getAllCategories_WithCategories_ShouldReturnListOfCategoryDtos() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(mapper.toResponseDto(category)).thenReturn(categoryResponseDto);

        List<CategoryResponseDto> actualResponseDtoList = categoryService.getAllCategories();

        assertEquals(1, actualResponseDtoList.size());
        assertEquals(categoryResponseDto, actualResponseDtoList.get(0));
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



