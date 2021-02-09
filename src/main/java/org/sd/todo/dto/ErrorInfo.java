package org.sd.todo.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorInfo {
    public final Date timestamp;
    public final int status;
    public final String error;
    public final String path;
    public final String message;

    public ErrorInfo(HttpStatus status, String path, String error, Exception exception) {
        this.timestamp = new Date();
        this.status = status.value();
        this.path = path;
        this.error = error;
        this.message = exception.getMessage();
    }
}