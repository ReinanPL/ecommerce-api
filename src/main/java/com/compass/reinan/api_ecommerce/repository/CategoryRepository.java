package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);
}
