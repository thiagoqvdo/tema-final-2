package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.exceptions;

public class InvalidRequestParamsException extends RuntimeException{
    private static final String message = "Request contains invalid params.";
    public InvalidRequestParamsException() {
        super(message);
    }

    public InvalidRequestParamsException(String message) {
        super(message);
    }

    public InvalidRequestParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestParamsException(Throwable cause) {
        super(cause);
    }

    public InvalidRequestParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
