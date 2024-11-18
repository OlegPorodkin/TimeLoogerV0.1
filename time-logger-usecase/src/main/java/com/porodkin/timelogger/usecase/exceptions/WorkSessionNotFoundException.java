package com.porodkin.timelogger.usecase.exceptions;

/**
 * Custom exception thrown when a {@link WorkSession} is not found by its unique identifier.
 */
public class WorkSessionNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code WorkSessionNotFoundException} with a detailed message.
     *
     * @param id the unique identifier of the {@link WorkSession} that was not found
     */
    public WorkSessionNotFoundException(String id) {
        super("WorkSession not found with id: " + id);
    }
}
