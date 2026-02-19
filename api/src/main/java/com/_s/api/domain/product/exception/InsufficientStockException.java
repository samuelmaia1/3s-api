package com._s.api.domain.product.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Estoque insuficiente");
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}
