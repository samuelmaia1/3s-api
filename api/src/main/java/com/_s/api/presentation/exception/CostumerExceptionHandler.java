package com._s.api.presentation.exception;

import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.exception.CostumerAlreadyExistsException;
import com._s.api.presentation.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CostumerExceptionHandler {
    @ExceptionHandler(CostumerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCostumerNotFound(CostumerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.buildError(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(CostumerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCostumerAlreadyExists(CostumerAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.buildValidationError(HttpStatus.CONFLICT, exception.getMessage(), exception.getFields()));
    }
}
