package com._s.api.domain.orderItem.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException() {
        super("Produto com estoque insuficiente para essa operação");
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}
