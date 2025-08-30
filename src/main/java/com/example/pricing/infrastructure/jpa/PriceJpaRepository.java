package com.example.pricing.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brandId = :brandId " +
           "AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC, p.priceList DESC")
    List<PriceEntity> findApplicable(@Param("applicationDate") LocalDateTime applicationDate,
                                     @Param("productId") long productId,
                                     @Param("brandId") long brandId);
}
