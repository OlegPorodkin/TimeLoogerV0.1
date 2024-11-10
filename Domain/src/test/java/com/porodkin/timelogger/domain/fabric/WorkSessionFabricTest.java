package com.porodkin.timelogger.domain.fabric;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkSessionFabricTest {

    private ZoneOffset offset = ZoneOffset.of("-03:30");

    @Test
    public void testCreateWorkSessionSuccessfully() {
        String userId = UUID.randomUUID().toString();
        UUID sessionId = UUID.randomUUID();
        OffsetDateTime start = OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset);

        WorkSession session = WorkSessionFabric.create(userId, sessionId, start);

        assertNotNull(session, "WorkSession should not be null");
        assertEquals(sessionId, session.getUuid(), "UUID should match the one provided");
        assertEquals(userId, session.getUserId(), "User ID should match the one provided");
        assertEquals(start, session.getDate(), "Date should match the start date");
        assertEquals(start.toOffsetTime(), session.getStartTime(), "Start time should match the one provided");
    }

    @Test
    public void testCreateWorkSessionWithNullStartTime() {
        String userId = UUID.randomUUID().toString();
        UUID sessionId = UUID.randomUUID();

        Exception exception = assertThrows(CreationWorkSessionException.class, () -> WorkSessionFabric.create(userId, sessionId, null));

        assertEquals("start is null", exception.getMessage(), "Exception message should indicate null start time");
    }
}