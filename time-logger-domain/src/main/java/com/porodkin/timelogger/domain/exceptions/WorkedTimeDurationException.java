package com.porodkin.timelogger.domain.exceptions;

/**
 * Thrown to indicate that the duration of work time is invalid according to specified rules.
 * This exception is typically used when validating the minimum length of work time duration.
 */
public class WorkedTimeDurationException extends RuntimeException {
    public WorkedTimeDurationException() {
        super();
    }
    public WorkedTimeDurationException(String message) {
        super(message);
    }
    public WorkedTimeDurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
