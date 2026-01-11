package com._s.api.response;

import java.time.LocalDateTime;

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
}
