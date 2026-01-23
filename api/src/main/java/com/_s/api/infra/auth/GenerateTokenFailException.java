package com._s.api.infra.auth;

public class GenerateTokenFailException extends RuntimeException{
    public GenerateTokenFailException() {
        super("Erro ao gerar token");
    }

    public GenerateTokenFailException(String message) {
        super(message);
    }
}
