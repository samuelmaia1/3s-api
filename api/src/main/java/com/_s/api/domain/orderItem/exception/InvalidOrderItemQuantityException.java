package com._s.api.domain.orderItem.exception;

public class InvalidOrderItemQuantityException extends RuntimeException{
    public InvalidOrderItemQuantityException() {
        super("Quantidade de itens inválida.");
    }

    public InvalidOrderItemQuantityException(String message) {
        super(message);
    }
}
