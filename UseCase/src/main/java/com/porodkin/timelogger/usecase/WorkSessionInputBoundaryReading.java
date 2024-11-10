package com.porodkin.timelogger.usecase;

/**
 * This interface defines the input boundary for reading work session data.
 */
public interface WorkSessionInputBoundaryReading {

    /**
     * Retrieves the work session identified by the given unique ID.
     *
     * @param workSessionId the unique identifier of the work session to be retrieved
     */
    void getWorkSessionById(String workSessionId);

    /**
     * Retrieves all existing work sessions.
     */
    void getAllWorkSessions();
}
