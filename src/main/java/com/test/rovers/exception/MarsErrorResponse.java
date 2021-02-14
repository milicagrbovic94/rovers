package com.test.rovers.exception;

public class MarsErrorResponse {
    private int Status;
    private String message;

    public MarsErrorResponse() {

    }

    public MarsErrorResponse(int status, String message) {
        Status = status;
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
