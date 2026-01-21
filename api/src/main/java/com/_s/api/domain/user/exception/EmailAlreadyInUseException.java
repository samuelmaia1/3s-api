package com._s.api.domain.user.exception;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    public EmailAlreadyInUseException() {
        super("Este e-mail já está em uso.");
    }
}
