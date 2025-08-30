package com.example.pricing.infrastructure.jpa;

import com.example.pricing.domain.Price;
import com.example.pricing.domain.PriceRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository repository;

    @Override
    public Optional<Price> findApplicablePrice(java.time.LocalDateTime applicationDate, long productId, long brandId) {
        return repository.findApplicable(applicationDate, productId, brandId).stream()
                .findFirst()
                .map(e -> new Price(
                        e.getBrandId(),
                        e.getProductId(),
                        e.getPriceList(),
                        e.getPriority(),
                        e.getStartDate(),
                        e.getEndDate(),
                        e.getPrice(),
                        e.getCurrency()
                ));
    }
}
