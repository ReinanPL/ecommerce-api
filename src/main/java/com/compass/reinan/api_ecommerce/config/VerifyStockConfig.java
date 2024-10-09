package com.compass.reinan.api_ecommerce.config;

import com.compass.reinan.api_ecommerce.domain.entity.StockReservation;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Status;
import com.compass.reinan.api_ecommerce.repository.SaleRepository;
import com.compass.reinan.api_ecommerce.repository.StockReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Component
@EnableScheduling
public class VerifyStockConfig {

    private final StockReservationRepository stockReservationRepository;
    private final SaleRepository saleRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredReservations() {
        var thirtyMinutesAgo = Instant.now().minusSeconds(1600);
        var expiredReservation = stockReservationRepository.findAllByCreatedAtBefore(thirtyMinutesAgo);
        for(StockReservation reservation : expiredReservation) {
            var sale = reservation.getSale();
            sale.setStatus(Status.CANCELED);
            saleRepository.save(sale);
            stockReservationRepository.delete(reservation);
        }
    }
}