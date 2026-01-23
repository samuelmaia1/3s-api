package com._s.api.infra.auth;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
