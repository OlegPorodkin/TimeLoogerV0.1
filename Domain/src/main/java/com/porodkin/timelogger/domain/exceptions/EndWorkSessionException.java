package com.porodkin.timelogger.domain.exceptions;

public class EndWorkSessionException extends RuntimeException{
    public EndWorkSessionException() {
    }

    public EndWorkSessionException(String message) {
        super(message);
    }

    public EndWorkSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
