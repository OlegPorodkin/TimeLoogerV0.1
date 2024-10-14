package com.porodkin.timelogger.domain.fabric;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkSessionFabricTest {

    @Test
    public void testCreateWorkSessionSuccessfully() {
        UUID sessionId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 9, 0);

        WorkSession session = WorkSessionFabric.create(sessionId, start);

        assertNotNull(session, "WorkSession should not be null");
        assertEquals(sessionId, session.getUuid(), "UUID should match the one provided");
        assertEquals(start.toLocalDate(), session.getDate(), "Date should match the start date");
        assertEquals(start.toLocalTime(), session.getStartTime(), "Start time should match the one provided");
    }

    @Test
    public void testCreateWorkSessionWithNullStartTime() {
        UUID sessionId = UUID.randomUUID();

        Exception exception = assertThrows(CreationWorkSessionException.class, () -> WorkSessionFabric.create(sessionId, null));

        assertEquals("start is null", exception.getMessage(), "Exception message should indicate null start time");
    }
}