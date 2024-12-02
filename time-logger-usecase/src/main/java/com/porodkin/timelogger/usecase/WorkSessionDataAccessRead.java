package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface WorkSessionDataAccessRead {
    /**
     * Retrieves all work sessions from the data store.
     *
     * @return a collection of {@link WorkSession} objects representing all stored work sessions
     */
    Collection<WorkSession> findAllWorkSessions(String userId);

    /**
     * Retrieves a specific work session by its unique identifier.
     *
     * @param userId    the unique identifier of the user
     * @param sessionId the unique identifier of the work session to be retrieved
     * @return the {@link WorkSession} object with the specified ID, or null if not found
     */
    WorkSession findByUserIdAndWorkSessionId(String userId, String sessionId) throws WorkSessionNotFoundException;

    /**
     * Retrieves the work session associated with the specified user ID.
     *
     * <p>This method searches for a {@link WorkSession} using the provided user ID.
     * If no work session is found for the given user ID, a {@link WorkSessionNotFoundException}
     * is thrown.
     *
     * @param userId the unique identifier of the user whose work session is to be retrieved.
     *               Must not be null or empty.
     * @return the {@link WorkSession} associated with the specified user ID.
     * @throws WorkSessionNotFoundException if no work session is found for the given user ID.
     * @throws IllegalArgumentException if the provided {@code userId} is null or empty.
     */
    WorkSession findWorkSessionByUserId(String userId);
}
