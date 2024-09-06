package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.active = true")
    Optional<Product> findProductByIdAndActive(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.active = true " +
            "AND p.quantityInStock > 0 " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findProductsActiveByFilters(
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);
}
