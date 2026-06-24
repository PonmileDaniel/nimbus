package com.purplelove.exception;

public class JsonException extends NimbusException {
    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);;
    }
    
}
