package com.example.pricing.application;

import com.example.pricing.domain.Price;
import com.example.pricing.domain.PriceRepositoryPort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceQueryServiceTest {

    @Test
    void returnsPriceWhenFound() {
        PriceRepositoryPort repo = (date, productId, brandId) -> Optional.of(
                new Price(1, 35455, 2, 1,
                        date.minusHours(1), date.plusHours(1),
                        new BigDecimal("25.45"), "EUR")
        );
        PriceQueryService service = new PriceQueryService(repo);
        Price p = service.getPrice(LocalDateTime.parse("2020-06-14T16:00:00"), 35455, 1);
        assertEquals(1, p.getBrandId());
        assertEquals(35455, p.getProductId());
        assertEquals(2, p.getPriceList());
    }

    @Test
    void throwsWhenNotFound() {
        PriceRepositoryPort repo = (date, productId, brandId) -> Optional.empty();
        PriceQueryService service = new PriceQueryService(repo);
        assertThrows(PriceNotFoundException.class, () ->
                service.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455, 1));
    }
}
