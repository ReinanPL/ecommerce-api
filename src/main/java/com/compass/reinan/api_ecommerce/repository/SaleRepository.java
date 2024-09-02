package com.compass.reinan.api_ecommerce.repository;

import com.compass.reinan.api_ecommerce.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.user.cpf = :cpf " +
            "AND FUNCTION('DAY', s.dateSale) = :day " +
            "AND FUNCTION('MONTH', s.dateSale) = :month " +
            "AND FUNCTION('YEAR', s.dateSale) = :year")
    List<Sale> findSalesByCpfAndDate(@Param("cpf") String cpf,
                                     @Param("day") int day,
                                     @Param("month") int month,
                                     @Param("year") int year);

    @Query("SELECT s FROM Sale s WHERE s.user.cpf = :cpf " +
            "AND FUNCTION('MONTH', s.dateSale) = :month " +
            "AND FUNCTION('YEAR', s.dateSale) = :year")
    List<Sale> findSalesByCpfAndMonth(@Param("cpf") String cpf,
                                      @Param("month") int month,
                                      @Param("year") int year);

    @Query("SELECT s FROM Sale s WHERE s.user.cpf = :cpf " +
            "AND s.dateSale BETWEEN :startOfWeek AND :endOfWeek")
    List<Sale> findSalesByCpfAndCurrentWeek(@Param("cpf") String cpf,
                                            @Param("startOfWeek") Instant startOfWeek,
                                            @Param("endOfWeek") Instant endOfWeek);
}

