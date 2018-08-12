package com.app.exceptions;

public class ProducerNotFoundException extends RuntimeException {
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
