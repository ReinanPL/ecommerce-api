package com.compass.reinan.api_ecommerce.domain;

import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.CategoryRepository;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.service.impl.ProductServiceImpl;
import com.compass.reinan.api_ecommerce.service.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compass.reinan.api_ecommerce.common.ProductConstants.*;
import static com.compass.reinan.api_ecommerce.common.ProductConstants.PRODUCT_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void saveProduct_WithValidData_ShouldSaveAndReturnResponseDto() {
        when(productRepository.existsByName(PRODUCT.getName())).thenReturn(false);
        when(mapper.toEntity(PRODUCT_REQUEST, categoryRepository)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        when(mapper.toResponse(PRODUCT)).thenReturn(PRODUCT_RESPONSE);

        ProductResponse actualResponseDto = productService.save(PRODUCT_REQUEST);

        assertThat(actualResponseDto).isEqualTo(PRODUCT_RESPONSE);
        verify(productRepository, times(1)).save(eq(PRODUCT));
        verify(productRepository, times(1)).existsByName(eq(PRODUCT.getName()));
    }

    @Test
    public void saveProduct_WithNameAlreadyRegistered_ShouldThrowDataUniqueViolationException() {
        when(mapper.toEntity(PRODUCT_REQUEST, categoryRepository)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenThrow(new DataUniqueViolationException("Product already exists"));

        assertThrows(DataUniqueViolationException.class, () -> productService.save(PRODUCT_REQUEST));
        verify(productRepository, times(1)).save(PRODUCT);
    }

    @Test
    public void findProductById_WithExistingId_ShouldReturnResponseDto() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        when(mapper.toResponse(PRODUCT)).thenReturn(PRODUCT_RESPONSE);

        ProductResponse actualResponseDto = productService.findById(1L);

        assertThat(actualResponseDto).isEqualTo(PRODUCT_RESPONSE);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void findProductById_WithNonExistingId_ShouldThrowEntityNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findById(1L));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteProduct_WhenProductExistsAndHasNoItemsSale_ShouldDeleteCategory() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));

        productService.deleteById(1L);

        verify(productRepository, times(1)).delete(PRODUCT);
    }

    @Test
    public void deleteProduct_WhenProductExistsAndHasItemsSale_ShouldInactiveCategory() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT_ACTIVE));
        when(productRepository.save(PRODUCT_ACTIVE)).thenReturn(PRODUCT_INACTIVE);

        productService.deleteById(1L);

        verify(productRepository, never()).delete(PRODUCT_ACTIVE);
        verify(productRepository, times(1)).save(PRODUCT_ACTIVE);
    }

    @Test
    public void inactiveProduct_WithProductAlreadyInactive_ShouldThrowCategoryActiveException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT_ALREADY_INACTIVE));

        assertThrows(EntityActiveStatusException.class, () -> productService.deleteById(1L));

        verify(productRepository, never()).save(any());
    }

    @Test
    public void activateProduct_WithInactiveProduct_ShouldReturnActivatedProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT_INACTIVE));
        when(productRepository.save(PRODUCT_INACTIVE)).thenReturn(PRODUCT_ACTIVE);
        when(mapper.toResponse(PRODUCT_ACTIVE)).thenReturn(PRODUCT_RESPONSE);

        ProductResponse actualResponseDtoList = productService.activeProduct(1L);

        verify(productRepository, times(1)).save(PRODUCT_INACTIVE);
        verify(productRepository, times(1)).findById(1L);
        assertThat(PRODUCT_RESPONSE.active()).isEqualTo(true);
    }

    @Test
    public void activateProduct_WithAlreadyActiveProduct_ShouldThrowCategoryActiveException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT_ALREADY_ACTIVE));

        assertThrows(EntityActiveStatusException.class, () -> productService.activeProduct(1L));

        verify(productRepository, never()).save(any());
    }

    @Test
    public void getAllProducts_WithProducts_ShouldReturnListOfProductsDto() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(PRODUCT));
        when(mapper.toResponse(PRODUCT)).thenReturn(PRODUCT_RESPONSE);

        List<ProductResponse> actualResponseDtoList = productService.findAll();

        assertEquals(1, actualResponseDtoList.size());
        assertEquals(PRODUCT_RESPONSE, actualResponseDtoList.get(0));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getAllProducts_WithNoProducts_ShouldReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductResponse> actualResponseDtoList = productService.findAll();

        assertEquals(0, actualResponseDtoList.size());
        verify(productRepository, times(1)).findAll();
    }
}
