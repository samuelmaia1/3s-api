package com._s.api.domain.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("Produto não encontrado");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
