package com._s.api.domain.rent.exception;

public class ProductUnavailableForPeriodException extends RuntimeException {
    public ProductUnavailableForPeriodException(String message) {
        super(message);
    }
}
