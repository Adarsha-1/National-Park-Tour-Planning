package org.example.model;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private HttpStatus status;
    private String message;

    public ErrorDetails(String message, HttpStatus badRequest) {
        this.message = message;
        this.status = badRequest;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
