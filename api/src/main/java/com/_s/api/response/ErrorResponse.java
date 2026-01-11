package com._s.api.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    private Integer status;
    private String error;
    private LocalDateTime time;

    public ErrorResponse(String message, Integer status, String error, LocalDateTime time) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.time = time;
    }

    public static ErrorResponse buildError(HttpStatus status, String message){
        return new ErrorResponse(message, status.value(), status.getReasonPhrase(), LocalDateTime.now());
    }
}
