package com._s.api.infra.exception;

import com._s.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {


    private ErrorResponse buildError(HttpStatus status, String message){
        return new ErrorResponse(message, status.value(), status.getReasonPhrase(), LocalDateTime.now());
    }
}
