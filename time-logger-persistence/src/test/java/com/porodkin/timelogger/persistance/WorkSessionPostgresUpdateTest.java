package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkSessionPostgresUpdateTest {

    @Mock
    private PostgresRepositoryJpa repository;

    @InjectMocks
    private WorkSessionPostgresUpdate workSessionPostgresUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateWorkSession_Success() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        UUID uuid = UUID.randomUUID();
        OffsetTime startTime = OffsetTime.parse("10:00:00-03:30");
        OffsetTime endTime = OffsetTime.parse("14:00:00-03:30");
        OffsetDateTime date = OffsetDateTime.parse("2023-11-17T10:00:00-03:30");

        WorkTime existingWorkTime = new WorkTime();
        existingWorkTime.setUserId(userId);
        existingWorkTime.setSessionId(uuid.toString());
        existingWorkTime.setStartTime(startTime);
        existingWorkTime.setDate(date);

        WorkSession inputWorkSession = new WorkSession(userId, uuid, date, startTime, endTime, null);

        when(repository.findByUserIdAndSessionId(userId, uuid.toString()))
                .thenReturn(Optional.of(existingWorkTime));

        // Act
        WorkSession updatedWorkSession = workSessionPostgresUpdate.updateWorkSession(inputWorkSession);

        // Assert
        assertNotNull(updatedWorkSession);
        assertEquals(endTime, updatedWorkSession.getEndTime());
        verify(repository, times(1)).save(existingWorkTime);
    }

    @Test
    void testUpdateWorkSession_WorkSessionNotFound() {
        // Arrange
        String userId = "user123";
        UUID uuid = UUID.randomUUID();

        WorkSession inputWorkSession = new WorkSession(userId, uuid, OffsetDateTime.parse("2023-11-17T10:00:00-03:30"));

        when(repository.findByUserIdAndSessionId(userId, uuid.toString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        WorkSessionNotFoundException exception = assertThrows(
                WorkSessionNotFoundException.class,
                () -> workSessionPostgresUpdate.updateWorkSession(inputWorkSession)
        );

        assertTrue(exception.getMessage().contains(uuid.toString()));
        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateWorkSession_EndTimeIsNull() {
        // Arrange
        String userId = "user123";
        UUID uuid = UUID.randomUUID();

        WorkTime existingWorkTime = new WorkTime();
        existingWorkTime.setUserId(userId);
        existingWorkTime.setSessionId(uuid.toString());

        WorkSession inputWorkSession = new WorkSession(userId, uuid, OffsetDateTime.parse("2023-11-17T10:00:00-03:30"));

        when(repository.findByUserIdAndSessionId(userId, uuid.toString()))
                .thenReturn(Optional.of(existingWorkTime));

        // Act & Assert
        EndWorkSessionException exception = assertThrows(
                EndWorkSessionException.class,
                () -> workSessionPostgresUpdate.updateWorkSession(inputWorkSession)
        );

        assertTrue(exception.getMessage().contains(uuid.toString()));
        verify(repository, never()).save(any());
    }

}