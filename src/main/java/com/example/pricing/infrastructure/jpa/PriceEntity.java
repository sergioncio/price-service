package com.example.pricing.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priceList;
    private long productId;
    private int priority;
    private BigDecimal price;
    @Column(name = "CURR")
    private String currency;
}
