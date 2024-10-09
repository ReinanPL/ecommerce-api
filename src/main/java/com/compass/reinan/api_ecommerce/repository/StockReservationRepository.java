package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.Product;
import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import com.compass.reinan.api_ecommerce.domain.entity.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {

    List<StockReservation> findBySale(Sale sale);

    List<StockReservation> findAllByCreatedAtBefore(Instant expiryDate);

    @Query("SELECT SUM(sr.quantity) FROM StockReservation sr WHERE sr.product = :product")
    Integer sumReservedStockByProduct(@Param("product") Product product);
}
