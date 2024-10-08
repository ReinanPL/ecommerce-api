package com.compass.reinan.api_ecommerce.config;

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

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredReservations() {
        var thirtyMinutesAgo = Instant.now().minusSeconds(1600);
        stockReservationRepository.deleteExpiredReservations(thirtyMinutesAgo);
    }
}