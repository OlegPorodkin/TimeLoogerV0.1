package com.porodkin.timelogger.domain.exceptions;

public class CreationWorkSessionException extends RuntimeException {
    public CreationWorkSessionException() {
    }

    public CreationWorkSessionException(String message) {
        super(message);
    }

    public CreationWorkSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
