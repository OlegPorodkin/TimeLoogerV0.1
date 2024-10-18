package com.porodkin.timelogger.domain.fabric;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class is a fabric to creation WorkSession
 *
 */
public class WorkSessionFabric {
    /**
     * Create new work session.
     *
     * @param sessionId identifier work session.
     * @param start work session date and time started.
     * @return new work session with date and time work session
     * @throws CreationWorkSessionException when start date/time is null.
     */
    public static WorkSession create(UUID sessionId, LocalDateTime start) throws CreationWorkSessionException {
        if (start == null) {
            throw new CreationWorkSessionException("start is null");
        }
        return new WorkSession(sessionId, start);
    }
}
