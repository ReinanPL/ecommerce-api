package com.compass.reinan.api_ecommerce.domain;

import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.repository.ProductRepository;
import com.compass.reinan.api_ecommerce.repository.SaleRepository;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.impl.SaleServiceImpl;
import com.compass.reinan.api_ecommerce.service.mapper.SaleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compass.reinan.api_ecommerce.common.ProductConstants.*;
import static com.compass.reinan.api_ecommerce.common.SaleConstants.*;
import static com.compass.reinan.api_ecommerce.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;


    @Mock
    private SaleMapper mapper;

    @Test
    void save_Success() {
        String userCpf = "52624127003";
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        when(productRepository.findById(2L)).thenReturn(Optional.of(PRODUCT));
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));
        when(mapper.toEntity(SALE_REQUEST_TWO_ITEM)).thenReturn(SALE_TWO_ITEM);
        when(saleRepository.save(SALE_TWO_ITEM)).thenReturn(SALE_TWO_ITEM);
        when(mapper.toResponse(SALE_TWO_ITEM)).thenReturn(SALE_RESPONSE_TWO_ITEM);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userCpf, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SaleResponse actualResponseDto = saleService.save(SALE_REQUEST_TWO_ITEM);

        assertThat(actualResponseDto).isEqualTo(SALE_RESPONSE_TWO_ITEM);
        verify(saleRepository, times(1)).save(eq(SALE_TWO_ITEM));
    }

    @Test
    public void createSale_WithNonexistentUser_ShouldThrowException() {
        when(userRepository.findById(EXISTING_CPF)).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> saleService.save(SALE_REQUEST));
    }

    @Test
    public void findSaleById_WithExistingId_ShouldReturnSaleResponse() {
        String userCpf = "52624127003";
        when(saleRepository.findById(1L)).thenReturn(Optional.of(SALE_TWO_ITEM));
        when(mapper.toResponse(SALE_TWO_ITEM)).thenReturn(SALE_RESPONSE_TWO_ITEM);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userCpf, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SaleResponse actualResponseDto = saleService.findById(1L);

        assertThat(actualResponseDto).isEqualTo(SALE_RESPONSE_TWO_ITEM);
        verify(saleRepository, times(1)).findById(1L);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void findSaleById_WithNonExistingId_ShouldThrowException() {
        when(saleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> saleService.findById(1L));

        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteSale_WhenSaleExists_ShouldDeleteSale() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(SALE));

        saleService.deleteById(1L);

        verify(saleRepository, times(1)).delete(SALE);
    }

    @Test
    public void cancelSale_ShouldReturnSaleResponseWithSaleCanceled(){
        String userCpf = "52624127003";
        when(saleRepository.findById(1L)).thenReturn(Optional.of(SALE));
        when(saleRepository.save(SALE)).thenReturn(SALE_CANCELED);
        when(mapper.toResponse(SALE_CANCELED)).thenReturn(SALE_RESPONSE_CANCELED);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userCpf, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SaleResponse actualResponseDto = saleService.cancelSale(1L);

        assertEquals(actualResponseDto.status(), "CANCELED");
        verify(saleRepository, times(1)).save(SALE);
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    public void updateSaleItemList_WithValidParameters_ShouldReturnSaleResponseWithUpdateSale(){
        String userCpf = "52624127003";
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        when(productRepository.findById(3L)).thenReturn(Optional.of(PRODUCT));
        when(saleRepository.findById(1L)).thenReturn(Optional.of(SALE_TWO_ITEM));
        when(saleRepository.save(SALE_TWO_ITEM)).thenReturn(SALE_THREE_ITEM);
        when(mapper.toResponse(SALE_THREE_ITEM)).thenReturn(SALE_RESPONSE_THREE_ITEM);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userCpf, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SaleResponse actualResponseDto = saleService.updateSale(1L, UPDATE_ITEM_SALE);

        assertEquals(actualResponseDto, SALE_RESPONSE_THREE_ITEM);
        verify(saleRepository, times(1)).save(SALE_TWO_ITEM);
        verify(saleRepository, times(1)).findById(1L);
        SecurityContextHolder.clearContext();
    }
    @Test
    public void patchSaleItemList_WithValidDeleteParametersAndUpdateItemNull_ShouldReturnSaleResponseWithItemSaleDeleted(){
        String userCpf = "52624127003";
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        when(saleRepository.findById(1L)).thenReturn(Optional.of(SALE_TWO_ITEM));
        when(saleRepository.save(SALE_TWO_ITEM)).thenReturn(SALE_ONE_ITEM);
        when(mapper.toResponse(SALE_ONE_ITEM)).thenReturn(SALE_RESPONSE_ONE_ITEM);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userCpf, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SaleResponse actualResponseDto = saleService.patchSale(1L, UPDATE_PATCH_ITEM_SALE);

        assertEquals(actualResponseDto, SALE_RESPONSE_ONE_ITEM);
        verify(saleRepository, times(1)).save(SALE_TWO_ITEM);
        verify(saleRepository, times(1)).findById(1L);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getAllSales_WithSales_ShouldReturnListOfSaleResponses() {
        when(saleRepository.findAll()).thenReturn(Collections.singletonList(SALE));
        when(mapper.toResponse(SALE)).thenReturn(SALE_RESPONSE);

        List<SaleResponse> actualResponseDtoList = saleService.findAll();

        assertEquals(1, actualResponseDtoList.size());
        assertEquals(SALE_RESPONSE, actualResponseDtoList.get(0));
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    public void getAllSales_WithNoSales_ShouldReturnEmptyList() {
        when(saleRepository.findAll()).thenReturn(Collections.emptyList());

        List<SaleResponse> actualResponseDtoList = saleService.findAll();

        assertEquals(0, actualResponseDtoList.size());
        verify(saleRepository, times(1)).findAll();
    }
}