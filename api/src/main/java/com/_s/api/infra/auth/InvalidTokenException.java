package com._s.api.infra.auth;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Este token está expirado ou não existe");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
