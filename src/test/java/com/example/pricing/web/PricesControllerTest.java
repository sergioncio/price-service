package com.example.pricing.web;

import com.example.pricing.application.PriceNotFoundException;
import com.example.pricing.application.PriceQueryService;
import com.example.pricing.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PricesControllerTest {

    @Test
    void returnsOk() {
        PriceQueryService service = mock(PriceQueryService.class);
        when(service.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455, 1))
                .thenReturn(new Price(1,35455,1,0,
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"),
                        new BigDecimal("35.50"), "EUR"));
        PricesController controller = new PricesController(service);
        ResponseEntity<?> resp = controller.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1L);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void adviceMapsNotFound() {
        GlobalExceptionHandler advice = new GlobalExceptionHandler();
        var response = advice.handleNotFound(new PriceNotFoundException("not found"));
        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody().get("message").toString().contains("not found"));
    }

    @Test
    void adviceMapsInternalServer() {
        GlobalExceptionHandler advice = new GlobalExceptionHandler();
        var response = advice.handleBadRequest(new RuntimeException("internal server error"));
        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().get("message").toString().contains("internal server error"));
    }
}
