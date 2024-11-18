package com.porodkin.timelogger.domain.fabric;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;

import java.time.*;
import java.util.UUID;

/**
 * This class is a fabric to creation WorkSession
 *
 */
public class WorkSessionFabric {
    /**
     * Create new work session.
     *
     * @param userId    the unique user identifier as a {@code Sting}
     * @param sessionId identifier work session.
     * @param start work session date and time started.
     * @return new work session with date and time work session
     * @throws CreationWorkSessionException when start date/time is null.
     */
    public static WorkSession create(String userId, UUID sessionId, OffsetDateTime start) throws CreationWorkSessionException {
        if (start == null) {
            throw new CreationWorkSessionException("start is null");
        }
        return new WorkSession(userId, sessionId, start);
    }

    /**
     * Creates a new {@code WorkSession} instance with the provided parameters.
     *
     * @param userId    the unique user identifier as a {@code Sting}
     * @param sessionId the unique session identifier as a {@code String} representing a valid UUID
     * @param date      the date of the session as an {@code OffsetDateTime}
     * @param start     the starting time of the session as an {@code OffsetTime}
     * @param end       the ending time of the session as an {@code OffsetTime}
     * @param duration  the duration of the session as a {@code Duration}
     * @return a new {@code WorkSession} instance
     * @throws CreationWorkSessionException if the provided {@code sessionId} is not a valid UUID
     */
    public static WorkSession create(
            String userId,
            String sessionId,
            OffsetDateTime date,
            OffsetTime start,
            OffsetTime end,
            Duration duration) throws CreationWorkSessionException {

        UUID uuidSession = UUID.fromString(sessionId);

        if (start == null) {
            throw new CreationWorkSessionException("start is null");
        }

        return new WorkSession(
                userId,
                uuidSession,
                date,
                start,
                end,
                duration
        );
    }

    public static WorkSession create(String userId, String sessionId, OffsetTime endTime) {
        return new WorkSession(userId, UUID.fromString(sessionId), null, null, endTime, null);
    }
}
