package com._s.api.presentation.exception;

import com._s.api.domain.user.exception.EmailAlreadyInUseException;
import com._s.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.buildError(HttpStatus.CONFLICT, exception.getMessage()));
    }
}
