package com._s.api.exception.product;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException() {
        super("Estoque insuficiente para este produto");
    }

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
