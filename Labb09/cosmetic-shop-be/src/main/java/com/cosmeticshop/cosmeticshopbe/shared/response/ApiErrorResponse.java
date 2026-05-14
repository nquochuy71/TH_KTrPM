package com.cosmeticshop.cosmeticshopbe.shared.response;

import java.time.LocalDateTime;

public class ApiErrorResponse {
    private boolean success;
    private int code;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(int code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
