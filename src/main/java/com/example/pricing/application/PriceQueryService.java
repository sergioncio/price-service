package com.example.pricing.application;

import com.example.pricing.domain.Price;
import com.example.pricing.domain.PriceRepositoryPort;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * Application service orchestrating the 'get price to apply' use case.
 */
@Service
@RequiredArgsConstructor
public class PriceQueryService {
    private final PriceRepositoryPort repository;

    /**
     * Returns the price to apply for the given inputs.
     * @throws PriceNotFoundException if there is no applicable price.
     */
    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        return repository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException("No applicable price found"));
    }
}
