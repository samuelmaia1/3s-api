package com._s.api.domain.costumer;

public class CostumerNotFoundException extends RuntimeException{
    public CostumerNotFoundException() {
        super("Cliente não encontrado");
    }

    public CostumerNotFoundException(String message) {
        super(message);
    }
}
