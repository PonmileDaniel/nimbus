package com.purplelove.exception;

public class NimbusException extends RuntimeException{
    public NimbusException(String message) {
        super(message);
    }

    public NimbusException(String message, Throwable cause) {
        super(message, cause);
    }
}
