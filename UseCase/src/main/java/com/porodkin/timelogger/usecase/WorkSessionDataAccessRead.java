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
    Optional<WorkSession> findByUserIdAndWorkSessionId(String userId, String sessionId) throws WorkSessionNotFoundException;
}
