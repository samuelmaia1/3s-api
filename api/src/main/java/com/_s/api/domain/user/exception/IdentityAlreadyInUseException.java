package com._s.api.domain.user.exception;

public class IdentityAlreadyInUseException extends RuntimeException{
    public IdentityAlreadyInUseException() {
        super("Este número de identidade já está em uso.");
    }

    public IdentityAlreadyInUseException(String message) {
        super(message);
    }
}
