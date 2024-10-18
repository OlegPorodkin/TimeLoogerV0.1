package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.Collection;
import java.util.Optional;

/**
 * This interface defines the data access operations for managing work sessions.
 */
public interface WorkSessionDataAccessBoundary {
    /**
     * Saves the provided work session data.
     *
     * @param workTime the work session data to be saved
     * @return the saved {@link WorkSession} object
     */
    WorkSession saveWorkSession(WorkSession workTime);

    /**
     * Retrieves all work sessions from the data store.
     *
     * @return a collection of {@link WorkSession} objects representing all stored work sessions
     */
    Collection<WorkSession> findAllWorkSessions();

    /**
     * Retrieves a specific work session by its unique identifier.
     *
     * @param id the unique identifier of the work session to be retrieved
     * @return the {@link WorkSession} object with the specified ID, or null if not found
     */
    Optional<WorkSession> findByWorkSessionId(String id) throws WorkSessionNotFoundException;

    /**
     * Updates an existing work session in the data store.
     *
     * @param workTime the {@link WorkSession} object containing updated information
     * @return the updated {@link WorkSession} object, or null if the update was not successful
     */
    WorkSession updateWorkSession(WorkSession workTime);

    /**
     * Deletes a work session from the data store based on its unique identifier.
     *
     * @param id the unique identifier of the work session to be deleted
     */
    void deleteWorkSession(String id);
}
