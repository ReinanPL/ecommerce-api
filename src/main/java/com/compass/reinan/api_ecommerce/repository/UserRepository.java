package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCpf(String cpf);
    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    void deleteByCpf(String cpf);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
}
