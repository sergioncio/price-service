package com.example.pricing.infrastructure.jpa;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceRepositoryAdapterTest {

    @Test
    void mapsEntityToDomain() {
        PriceJpaRepository jpa = Mockito.mock(PriceJpaRepository.class);
        PriceEntity e = new PriceEntity();
        e.setBrandId(1);
        e.setProductId(35455);
        e.setPriceList(2);
        e.setPriority(1);
        e.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        e.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        e.setPrice(new BigDecimal("25.45"));
        e.setCurrency("EUR");

        when(jpa.findApplicable(LocalDateTime.parse("2020-06-14T16:00:00"), 35455, 1))
                .thenReturn(List.of(e));

        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(jpa);
        var result = adapter.findApplicablePrice(LocalDateTime.parse("2020-06-14T16:00:00"), 35455, 1);
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getPriceList());
    }

    @Test
    void emptyWhenNoEntity() {
        PriceJpaRepository jpa = Mockito.mock(PriceJpaRepository.class);
        when(jpa.findApplicable(LocalDateTime.parse("2020-06-14T10:00:00"), 35455, 1))
                .thenReturn(List.of());
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(jpa);
        assertEquals(Optional.empty(),
                adapter.findApplicablePrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455, 1));
    }
}
