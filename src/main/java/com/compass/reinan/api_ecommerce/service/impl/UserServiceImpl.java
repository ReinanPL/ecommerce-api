package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.UpdateAddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.CreateUserRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Role;
import com.compass.reinan.api_ecommerce.exception.DataUniqueViolationException;
import com.compass.reinan.api_ecommerce.exception.EntityActiveStatusException;
import com.compass.reinan.api_ecommerce.exception.PasswordInvalidException;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.UserService;
import com.compass.reinan.api_ecommerce.service.mapper.UserMapper;
import com.compass.reinan.api_ecommerce.util.EntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse saveUser(CreateUserRequest userRequest) {
        var user = mapper.toEntity(userRequest);

        Optional.of(userRepository.existsByCpf(user.getCpf()))
                .filter(exists ->!exists)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("Cpf '%s' already exists", userRequest.cpf())));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByCpf(String cpf) {
        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);
        return mapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserByCpf(String cpf) {
        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);

        Optional.of(user.getSales().isEmpty())
                        .filter(empty -> empty)
                        .orElseThrow(() -> new EntityActiveStatusException(String.format("User with CPF: '%s' has associated sales and cannot be deleted. Remove the sales first.", cpf)));

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserEmail(String cpf, String email){
        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);

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

        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);
        Optional.of(user)
                .filter(u -> passwordEncoder.matches(oldPassword, user.getPassword()))
                .orElseThrow(() -> new PasswordInvalidException("Old password does not match."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserRole(String cpf, String role) {
        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);

        Optional.of(Objects.equals(user.getRole().toString(), role.toUpperCase()))
                .filter(sameRole ->!sameRole)
                .orElseThrow(() -> new DataUniqueViolationException(String.format("The user already have this role: '%s'", role)));

        user.setRole(Role.valueOf(role.toUpperCase()));
        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUserAddress(String cpf, UpdateAddressRequest addressDto){
        var user = EntityUtils.getEntityOrThrow(cpf, User.class, userRepository);

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
