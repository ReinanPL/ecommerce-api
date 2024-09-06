package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.UpdateAddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.CreateUserRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(CreateUserRequest userRequest);
    UserResponse getUserByCpf(String cpf);
    void deleteUserByCpf(String cpf);
    UserResponse updateUserEmail(String cpf, String email);
    void updateUserPassword(String cpf, String oldPassword, String newPassword, String confirmPassword);
    UserResponse updateUserRole(String cpf, String role);
    UserResponse updateUserAddress(String cpf, UpdateAddressRequest addressDto);
    List<UserResponse> getAllUsers();
}
