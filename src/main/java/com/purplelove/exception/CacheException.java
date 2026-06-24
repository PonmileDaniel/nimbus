package com.purplelove.exception;

public class CacheException extends NimbusException {
    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
