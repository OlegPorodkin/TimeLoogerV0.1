package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkSessionPostgresReadTest {

    @Mock
    private PostgresRepositoryJpa repository;

    @InjectMocks
    private WorkSessionPostgresRead workSessionPostgresRead;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllWorkSessions_WithResults() {
        // Arrange
        String userId = "user123";
        WorkTime workTime1 = createMockWorkTime("user123", UUID.randomUUID().toString(), "2024-11-15T08:00:00Z", "2024-11-15T12:00:00Z", Duration.ofHours(4));
        WorkTime workTime2 = createMockWorkTime("user123", UUID.randomUUID().toString(), "2024-11-15T13:00:00Z", "2024-11-15T17:00:00Z", Duration.ofHours(4));
        when(repository.findAll()).thenReturn(Arrays.asList(workTime1, workTime2));

        // Act
        var result = workSessionPostgresRead.findAllWorkSessions(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAllWorkSessions_NoResults() {
        // Arrange
        String userId = "user123";
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        var result = workSessionPostgresRead.findAllWorkSessions(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByUserIdAndWorkSessionId_Success() throws WorkSessionNotFoundException {
        // Arrange
        String userId = "user123";
        String sessionId = UUID.randomUUID().toString();
        WorkTime workTime = createMockWorkTime("user123", sessionId, "2024-11-15T08:00:00Z", "2024-11-15T12:00:00Z", Duration.ofHours(4));
        when(repository.findByUserIdAndSessionId(userId, sessionId)).thenReturn(Optional.of(workTime));

        // Act
        var result = workSessionPostgresRead.findByUserIdAndWorkSessionId(userId, sessionId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(sessionId, result.getUuid().toString());
        verify(repository, times(1)).findByUserIdAndSessionId(userId, sessionId);
    }

    @Test
    void testFindByUserIdAndWorkSessionId_NotFound() {
        // Arrange
        String userId = "user123";
        String sessionId = "session1";
        when(repository.findByUserIdAndSessionId(userId, sessionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WorkSessionNotFoundException.class,
                () -> workSessionPostgresRead.findByUserIdAndWorkSessionId(userId, sessionId));
        verify(repository, times(1)).findByUserIdAndSessionId(userId, sessionId);
    }

    private WorkTime createMockWorkTime(String userId, String sessionId, String startTime, String endTime, Duration duration) {
        WorkTime workTime = new WorkTime();
        workTime.setUserId(userId);
        workTime.setSessionId(sessionId);
        workTime.setDate(OffsetDateTime.parse(startTime));
        workTime.setStartTime(OffsetDateTime.parse(startTime).toOffsetTime());
        workTime.setEndTime(OffsetDateTime.parse(endTime).toOffsetTime());
        workTime.setDuration(duration);
        return workTime;
    }

}