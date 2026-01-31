package org.example.onlinebankmanagementbackend.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse
{
    private final int status;
    private final String message;
    private final String error;
    private final LocalDateTime timestamp;

    public ErrorResponse(int status, String message, String error, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }
}
