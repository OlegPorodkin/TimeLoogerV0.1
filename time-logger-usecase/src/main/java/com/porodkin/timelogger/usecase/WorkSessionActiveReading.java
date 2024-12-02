package com.porodkin.timelogger.usecase;

/**
 * This interface defines the input boundary for reading work session data.
 */
public interface WorkSessionActiveReading {

    /**
     * Retrieves the work session identified by the given unique ID.
     *
     * @param userId the unique identifier of the work session to be retrieved
     */
    void getCurrentWorkSessionByUserId(String userId);
}
