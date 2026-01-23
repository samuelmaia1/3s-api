package com._s.api.presentation.exception;

import com._s.api.infra.auth.GenerateTokenFailException;
import com._s.api.infra.auth.InvalidCredentialsException;
import com._s.api.presentation.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.buildError(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }

    @ExceptionHandler(GenerateTokenFailException.class)
    public ResponseEntity<ErrorResponse> handleGenTokenFail(GenerateTokenFailException exception) {
        return ResponseEntity.internalServerError().body(
                ErrorResponse.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar token para autenticação")
        );
    }
}
