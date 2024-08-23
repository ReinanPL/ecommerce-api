package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
