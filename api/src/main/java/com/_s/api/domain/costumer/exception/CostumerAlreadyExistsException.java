package com._s.api.domain.costumer.exception;

public class CostumerAlreadyExistsException extends RuntimeException {
    private String field;

    public CostumerAlreadyExistsException() {
        super("Cliente já existe.");
    }

    public CostumerAlreadyExistsException(String message, String field) {
        super(message);
        this.field = field;
    }

    public CostumerAlreadyExistsException(String message) {
        super(message);
    }
}
