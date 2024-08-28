package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.AddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.UserCreateRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.PasswordInvalidException;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.UserService;
import com.compass.reinan.api_ecommerce.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse saveUser(UserCreateRequest userRequest) {
        Optional.of(userRepository.existsByCpf(userRequest.cpf()))
                .filter(exists ->!exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Cpf '%s' already exists", userRequest.cpf())));
        return mapper.toResponse(userRepository.save(mapper.toEntity(userRequest)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf)
               .map(mapper::toResponse)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
    }

    @Override
    @Transactional
    public void deleteUserByCpf(String cpf) {
        var user = userRepository.findByCpf(cpf)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserEmail(String cpf, String email){
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
        Optional.of(userRepository.existsByEmail(email))
                 .filter(exists ->!exists)
                 .orElseThrow(() -> new DataUniqueViolationException(String.format("Email '%s' already exists", email)));
        user.setEmail(email);
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void updateUserPassword(String cpf, String oldPassword, String newPassword, String confirmPassword){
        Optional.of(newPassword)
                .filter(p -> p.equals(confirmPassword))
                .orElseThrow(() -> new PasswordInvalidException("New password does not match with confirm password."));

        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
        Optional.of(user)
                .filter(u -> oldPassword.equals(u.getPassword()))
                .orElseThrow(() -> new PasswordInvalidException("Old password does not match."));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserRole(String cpf, String role) {
        var user = userRepository.findByCpf(cpf)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
        if(Objects.equals(user.getRole().toString(), role.toUpperCase())){
            throw new DataUniqueViolationException(String.format("The user already have this role: '%s'", role));
        }
        user.setRole(User.Role.valueOf(role.toUpperCase()));
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUserAddress(String cpf, AddressRequest addressDto){
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cpf: '%s' not found ", cpf)));
        var addressUpdate = mapper.toEntityAddress(addressDto);
        addressUpdate.setId(user.getAddress().getId());
        user.setAddress(addressUpdate);
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
               .map(mapper::toResponse)
               .collect(Collectors.toList());
    }
}
