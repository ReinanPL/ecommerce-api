package com.compass.reinan.api_ecommerce.domain;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.PasswordInvalidException;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.impl.UserServiceImpl;
import com.compass.reinan.api_ecommerce.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compass.reinan.api_ecommerce.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, mapper, passwordEncoder);
    }

    @Test
    public void saveUser_withValidData_shouldSaveAndReturnResponseDto() {
        when(mapper.toEntity(USER_REQUEST)).thenReturn(USER_EXISTING);
        when(mapper.toResponse(USER_EXISTING)).thenReturn(USER_RESPONSE);
        when(userRepository.save(USER_EXISTING)).thenReturn(USER_EXISTING);

        UserResponse actualResponseDto = userService.saveUser(USER_REQUEST);

        assertThat(actualResponseDto).isEqualTo(USER_RESPONSE);
        verify(userRepository, times(1)).save(eq(USER_EXISTING));
    }

    @Test
    public void saveUser_withExistingCpf_shouldThrowDataUniqueViolationException() {
        when(mapper.toEntity(USER_REQUEST)).thenReturn(USER_EXISTING);
        when(userRepository.save(USER_EXISTING)).thenThrow(new DataUniqueViolationException("Category already exists"));

        assertThrows(DataUniqueViolationException.class, () -> userService.saveUser(USER_REQUEST));
        verify(userRepository, times(1)).save(USER_EXISTING);
    }

    @Test
    public void getUserByCpf_withExistingCpf_shouldReturnResponseDto() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));
        when(mapper.toResponse(USER_EXISTING)).thenReturn(USER_RESPONSE);

        UserResponse actualResponseDto = userService.getUserByCpf(EXISTING_CPF);

        assertThat(actualResponseDto).isEqualTo(USER_RESPONSE);
        verify(userRepository, times(1)).findById(EXISTING_CPF);
    }

    @Test
    public void getUserByCpf_withNonexistentCpf_shouldThrowEntityNotFoundException() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserByCpf(EXISTING_CPF));

        verify(userRepository, times(1)).findById(EXISTING_CPF);
    }

    @Test
    public void deleteUserByCpf_withExistingCpf_shouldDeleteUser() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));

        userService.deleteUserByCpf(EXISTING_CPF);

        verify(userRepository, times(1)).delete(USER_EXISTING);
    }

    @Test
    public void deleteUserByCpf_withNonExistingCpf_shouldThrowEntityNotFoundException() {
        when(userRepository.findById(NON_EXISTING_CPF)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserByCpf(NON_EXISTING_CPF));
    }

    @Test
    public void updateUserEmail_withValidData_shouldUpdateEmailAndReturnUpdatedUser() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));
        when(userRepository.existsByEmail(NEW_EMAIL)).thenReturn(false);
        when(userRepository.save(USER_EXISTING)).thenReturn(NEW_USER_EMAIL);
        when(mapper.toResponse(NEW_USER_EMAIL)).thenReturn(NEW_USER_EMAIL_RESPONSE);

        UserResponse response = userService.updateUserEmail(EXISTING_CPF, NEW_EMAIL);

        assertEquals(response.email(), NEW_EMAIL);
        verify(userRepository, times(1)).findById(EXISTING_CPF);
    }

    @Test
    public void updateUserEmail_withExistingEmail_shouldThrowDataUniqueViolationException() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(new User()));
        when(userRepository.existsByEmail(NEW_EMAIL)).thenReturn(true);

        assertThrows(DataUniqueViolationException.class, () -> userService.updateUserEmail(EXISTING_CPF, NEW_EMAIL));
    }

    @Test
    public void setPassword_WithValidData() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_PASSWORD_ENCRYPTED));

        userService.updateUserPassword(EXISTING_CPF, "123456", "new_password", "new_password");

        var user = userRepository.findById(EXISTING_CPF);

        assertTrue(passwordEncoder.matches("new_password", user.get().getPassword()));
        verify(userRepository, times(2)).findById(EXISTING_CPF);
    }

    @Test
    public void updateUserPassword_withInvalidConfirmation_shouldThrowPasswordInvalidException() {
        assertThrows(PasswordInvalidException.class, () -> userService.updateUserPassword(EXISTING_CPF, "old_password", "new_password", "invalid_password"));

        verify(userRepository, never()).findById(anyString());
    }

    @Test
    public void updateUserPassword_withInvalidOldPassword_shouldThrowPasswordInvalidException() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));

        assertThrows(PasswordInvalidException.class, () -> userService.updateUserPassword(EXISTING_CPF, "invalidOld_password", "new_password", "new_password"));
        verify(userRepository, never()).save(USER_EXISTING);
    }

    @Test
    public void updateUserRole_withValidData_shouldUpdateRoleAndReturnResponse() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));
        when(userRepository.save(USER_EXISTING)).thenReturn(NEW_USER_ROLE);
        when(mapper.toResponse(NEW_USER_ROLE)).thenReturn(NEW_USER_ROLE_RESPONSE);

        UserResponse response = userService.updateUserRole(EXISTING_CPF, "ADMIN");

        assertThat(response.role()).isEqualTo("ADMIN");
        verify(userRepository, times(1)).findById(EXISTING_CPF);
        verify(userRepository, times(1)).save(USER_EXISTING);
    }

    @Test
    public void updateUserRole_UpdateToAdminWithRoleAdmin_shouldThrowDataUniqueViolationException() {
        when(userRepository.findById(EXISTING_CPF_ADMIN)).thenReturn(Optional.of(USER_ADMIN));

        assertThrows(DataUniqueViolationException.class, () -> userService.updateUserRole(EXISTING_CPF_ADMIN, "ADMIN"));
        verify(userRepository, never()).save(USER_ADMIN);
    }

    @Test

    public void updateUserAddress_withValidData_shouldUpdateAddressAndReturnResponse() {
        when(userRepository.findById(EXISTING_CPF)).thenReturn(Optional.of(USER_EXISTING));
        when(mapper.toEntityAddress(ADDRESS_UPDATE_REQUEST)).thenReturn(ADDRESS_UPDATE);
        when(userRepository.save(USER_EXISTING)).thenReturn(USER_ADDRESS_UPDATE);
        when(mapper.toResponse(USER_ADDRESS_UPDATE)).thenReturn(NEW_USER_ADDRESS_RESPONSE);

        UserResponse response = userService.updateUserAddress(EXISTING_CPF, ADDRESS_UPDATE_REQUEST);

        assertThat(response.address()).isEqualTo(USER_ADDRESS_UPDATE.getAddress());
        verify(userRepository, times(1)).findById(EXISTING_CPF);
        verify(userRepository, times(1)).save(USER_EXISTING);
    }

    @Test
    public void updateUserAddress_withNonExistentUser_shouldThrowEntityNotFoundException() {
        when(userRepository.findById(NON_EXISTING_CPF)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUserAddress(NON_EXISTING_CPF, ADDRESS_UPDATE_REQUEST));
        verify(userRepository, never()).save(USER_EXISTING);
    }

    @Test
    public void getAllUsers_shouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(USER_EXISTING));
        when(mapper.toResponse(USER_EXISTING)).thenReturn(USER_RESPONSE);

        List<UserResponse> response = userService.getAllUsers();

        assertEquals(1, response.size());
        assertEquals(USER_RESPONSE, response.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAllUsers_WithNoCategories_ShouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> actualResponseDtoList = userService.getAllUsers();

        assertEquals(0, actualResponseDtoList.size());
        verify(userRepository, times(1)).findAll();
    }
}
