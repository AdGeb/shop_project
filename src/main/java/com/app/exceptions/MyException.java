package com.app.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String errorMessage;
    private LocalDateTime errorDateTime;

    @Override
    public String getMessage() {
        return "[ " + errorDateTime + " ]: " + errorMessage;
    }

    public MyException(String errorMessage, LocalDateTime errorDateTime) {
        this.errorMessage = errorMessage;
        this.errorDateTime = errorDateTime;
    }
}
