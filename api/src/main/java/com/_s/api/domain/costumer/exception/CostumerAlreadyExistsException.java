package com._s.api.domain.costumer.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CostumerAlreadyExistsException extends RuntimeException {
    private Map<String, String> fields;

    public CostumerAlreadyExistsException() {
        super("Cliente já existe.");
    }

    public CostumerAlreadyExistsException(String message, Map<String, String> fields) {
        super(message);
        this.fields = fields;
    }

    public CostumerAlreadyExistsException(String message) {
        super(message);
    }
}
