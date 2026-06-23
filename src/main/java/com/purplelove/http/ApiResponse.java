package com.purplelove.http;

public record ApiResponse<T>(
    int statusCode,
    String body,
    T data
) {
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }
}
