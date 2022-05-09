package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.exceptions;

public class InvalidHystrixCommandException extends RuntimeException{
    private static final String message = "Invalid hystrix command.";

    public InvalidHystrixCommandException() {
        super(message);
    }

    public InvalidHystrixCommandException(String message) {
        super(message);
    }

    public InvalidHystrixCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHystrixCommandException(Throwable cause) {
        super(cause);
    }

    public InvalidHystrixCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
