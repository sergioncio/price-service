package com.example.pricing.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Output port for querying prices from a persistence adapter.
 */
public interface PriceRepositoryPort {
    /**
     * Finds the highest priority applicable price for a product and brand at a given application date.
     * @param applicationDate Date and time when price should apply.
     * @param productId Product identifier.
     * @param brandId Brand identifier.
     * @return Optional price if found.
     */
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, long productId, long brandId);
}
