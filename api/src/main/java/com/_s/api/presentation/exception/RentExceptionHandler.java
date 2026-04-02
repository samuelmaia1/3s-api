package com._s.api.presentation.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com._s.api.domain.rent.exception.ProductUnavailableForPeriodException;
import com._s.api.domain.rent.exception.RentNotFoundException;
import com._s.api.presentation.response.ErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RentExceptionHandler {

    @ExceptionHandler(ProductUnavailableForPeriodException.class)
    public ResponseEntity<ErrorResponse> handleProductUnavailable(ProductUnavailableForPeriodException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.buildError(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRentNotFound(RentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.buildError(HttpStatus.NOT_FOUND, exception.getMessage()));
    }
}
