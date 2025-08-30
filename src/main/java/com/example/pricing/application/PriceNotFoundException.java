package com.example.pricing.application;

/** Exception raised when no price is found. */
public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) { super(message); }
}
