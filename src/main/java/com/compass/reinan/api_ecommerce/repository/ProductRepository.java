package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);
}
