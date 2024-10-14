package com.porodkin.timelogger.domain;

import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.WorkedTimeDurationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkSessionTest {
    @Test
    public void testWorkSessionCreation() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime startDateTime = LocalDateTime.of(2024, 10, 10, 9, 0);
        WorkSession session = new WorkSession(uuid, startDateTime);

        assertEquals(uuid, session.getUuid(), "UUID should match the one provided");
        assertEquals(startDateTime.toLocalDate(), session.getDate(), "Date should match the start date");
        assertEquals(startDateTime.toLocalTime(), session.getStartTime(), "Start time should match the one provided");
        assertNull(session.getEndTime(), "End time should be null upon creation");
        assertNull(session.getDuration(), "Duration should be null upon creation");
    }

    @Test
    public void testEndWorkSession() {
        WorkSession session = new WorkSession(UUID.randomUUID(), LocalDateTime.of(2023, 10, 10, 9, 0));
        LocalTime endTime = LocalTime.of(17, 0);
        session.endWorkSession(endTime);

        assertEquals(endTime, session.getEndTime(), "End time should be set correctly");
    }

    @Test
    public void testCalculateWorkDuration() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 9, 0));
        session.endWorkSession(LocalTime.of(17, 30));
        session.calculateWorkDuration();

        LocalTime expectedDuration = LocalTime.of(8, 30);
        assertEquals(expectedDuration, session.getDuration(), "Duration should be 8 hours and 30 minutes");
    }

    @Test
    public void testCalculateWorkDurationExactlyOneHour() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 9, 0));
        session.endWorkSession(LocalTime.of(10, 0));
        session.calculateWorkDuration();

        LocalTime expectedDuration = LocalTime.of(1, 0);
        assertEquals(expectedDuration, session.getDuration(), "Duration should be exactly 1 hour");
    }

    @Test
    public void testCalculateWorkDurationWithMinutes() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 8, 15));
        session.endWorkSession(LocalTime.of(16, 45));
        session.calculateWorkDuration();

        LocalTime expectedDuration = LocalTime.of(8, 30);
        assertEquals(expectedDuration, session.getDuration(), "Duration should be 8 hours and 30 minutes");
    }

    @Test
    public void testCalculateWorkDurationMaximumAllowed() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 0, 0));
        session.endWorkSession(LocalTime.of(23, 59));
        session.calculateWorkDuration();

        LocalTime expectedDuration = LocalTime.of(23, 59);
        assertEquals(expectedDuration, session.getDuration(), "Duration should be 23 hours and 59 minutes");
    }

    @Test
    public void testCalculateWorkDurationThrowsExceptionForShortDuration() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 9, 0));
        session.endWorkSession(LocalTime.of(9, 30));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Exception message should match");
    }

    @Test
    public void testCalculateWorkDurationThrowsExceptionForNegativeDuration() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 17, 0));
        session.endWorkSession(LocalTime.of(9, 0));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Exception message should match for negative duration");
    }

    @Test
    public void testCalculateWorkDurationWithNullEndTime() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 9, 0));

        Exception exception = assertThrows(EndWorkSessionException.class, session::calculateWorkDuration);
        assertTrue(exception.getMessage().contains("endTime"),
                "Exception message should indicate endTime is null");
    }

    @Test
    public void testEndWorkSessionWithNull() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 9, 0));

        assertThrows(EndWorkSessionException.class, () -> session.endWorkSession(null),
                "Ending work session with null should throw EndWorkSessionException");
    }

    @Test
    public void testCalculateWorkDurationHandlesMidnightCrossing() {
        WorkSession session = new WorkSession(UUID.randomUUID(),
                LocalDateTime.of(2023, 10, 10, 22, 0));
        session.endWorkSession(LocalTime.of(2, 0));

        Exception exception = assertThrows(WorkedTimeDurationException.class, session::calculateWorkDuration);
        assertEquals("Work time duration cannot be less than 1h", exception.getMessage(),
                "Crossing midnight is not supported and should throw exception");
    }
}