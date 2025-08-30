package com.example.pricing.web;

import com.example.pricing.application.PriceQueryService;
import com.example.pricing.domain.Price;
import com.example.pricing.generated.api.PricesApi;
import com.example.pricing.generated.model.PriceResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * REST controller implementing the generated OpenAPI interface.
 */
@RestController
@Validated
@RequiredArgsConstructor
public class PricesController implements PricesApi {

    private final PriceQueryService service;

    @Override
    public ResponseEntity<PriceResponse> getPrice(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            Long productId,
            Long brandId) {
        Price p = service.getPrice(applicationDate, productId, brandId);
        PriceResponse resp = new PriceResponse()
                .productId(p.getProductId())
                .brandId(p.getBrandId())
                .priceList(p.getPriceList())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .price(p.getPrice())
                .curr(p.getCurrency());
        return ResponseEntity.ok(resp);
    }
}
