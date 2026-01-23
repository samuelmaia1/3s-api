package com._s.api.presentation.exception;

import com._s.api.domain.user.exception.EmailAlreadyInUseException;
import com._s.api.domain.user.exception.IdentityAlreadyInUseException;
import com._s.api.presentation.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler  {
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.buildError(HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(IdentityAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleIdentityAlreadyInUse(IdentityAlreadyInUseException exception) {
        Map<String, String> fields = new HashMap<>();
        fields.put("CPF", "Este CPF já foi cadastrado em outra conta.");

        ErrorResponse error = ErrorResponse.buildError(HttpStatus.CONFLICT, exception.getMessage());
        error.setFields(fields);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}
