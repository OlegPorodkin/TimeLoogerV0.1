package com.porodkin.timelogger.domain;

import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.WorkedTimeDurationException;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkSessionTest {

    private ZoneOffset offset = ZoneOffset.of("-03:30");

    @Test
    public void testWorkSessionCreation() {
        String userId = UUID.randomUUID().toString();
        UUID uuid = UUID.randomUUID();
        OffsetDateTime startDateTime = OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(7, 30, 0), offset);
        WorkSession session = new WorkSession(userId, uuid, startDateTime);

        assertEquals(uuid, session.getUuid(), "UUID should match the one provided");
        assertEquals(startDateTime, session.getDate(), "Date should match the start date");
        assertEquals(startDateTime.toOffsetTime(), session.getStartTime(), "Start time should match the one provided");
        assertNull(session.getEndTime(), "End time should be null upon creation");
        assertNull(session.getDuration(), "Duration should be null upon creation");
    }

    @Test
    public void testEndWorkSession() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(), OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));
        OffsetTime endTime = OffsetTime.of(LocalTime.of(17, 0), offset);
        session.endWorkSession(endTime);

        assertEquals(endTime, session.getEndTime(), "End time should be set correctly");
    }

    @Test
    public void testCalculateWorkDuration() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(17, 30), offset));
        session.calculateWorkDuration();

        OffsetTime expectedDuration = OffsetTime.of(LocalTime.of(8, 30), offset);
        assertEquals(expectedDuration, OffsetTime.of(LocalTime.MIDNIGHT.plus(session.getDuration()), offset), "Duration should be 8 hours and 30 minutes");
    }

    @Test
    public void testCalculateWorkDurationExactlyOneHour() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(10, 0), offset));
        session.calculateWorkDuration();

        OffsetTime expectedDuration = OffsetTime.of(LocalTime.of(1, 0), offset);
        assertEquals(expectedDuration, OffsetTime.of(LocalTime.MIDNIGHT.plus(session.getDuration()), offset), "Duration should be exactly 1 hour");
    }

    @Test
    public void testCalculateWorkDurationWithMinutes() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(8, 15, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(16, 45), offset));
        session.calculateWorkDuration();

        OffsetTime expectedDuration = OffsetTime.of(LocalTime.of(8, 30), offset);
        assertEquals(expectedDuration, OffsetTime.of(LocalTime.MIDNIGHT.plus(session.getDuration()), offset), "Duration should be 8 hours and 30 minutes");
    }

    @Test
    public void testCalculateWorkDurationMaximumAllowed() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(0, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(23, 59), offset));
        session.calculateWorkDuration();

        OffsetTime expectedDuration = OffsetTime.of(LocalTime.of(23, 59), offset);
        assertEquals(expectedDuration, OffsetTime.of(LocalTime.MIDNIGHT.plus(session.getDuration()), offset), "Duration should be 23 hours and 59 minutes");
    }

    @Test
    public void testCalculateWorkDurationThrowsExceptionForShortDuration() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(9, 30), offset));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Exception message should match");
    }

    @Test
    public void testCalculateWorkDurationThrowsExceptionForNegativeDuration() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(17, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(9, 0), offset));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Exception message should match for negative duration");
    }

    @Test
    public void testCalculateWorkDurationWithNullEndTime() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));

        Exception exception = assertThrows(EndWorkSessionException.class, session::calculateWorkDuration);
        assertTrue(exception.getMessage().contains("endTime"),
                "Exception message should indicate endTime is null");
    }

    @Test
    public void testEndWorkSessionWithNull() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(9, 0, 0), offset));

        assertThrows(EndWorkSessionException.class, () -> session.endWorkSession(null),
                "Ending work session with null should throw EndWorkSessionException");
    }

    @Test
    public void testCalculateWorkDurationHandlesMidnightCrossing() {
        String userId = UUID.randomUUID().toString();
        WorkSession session = new WorkSession(userId, UUID.randomUUID(),
                OffsetDateTime.of(LocalDate.of(2024, 10, 25), LocalTime.of(22, 0, 0), offset));
        session.endWorkSession(OffsetTime.of(LocalTime.of(2, 0), offset));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Crossing midnight is not supported and should throw exception");
    }
}