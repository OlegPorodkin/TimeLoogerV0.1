package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.persistance.mappers.WorkTimeToWorkSessionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkSessionPostgresUpdateTest {

    @Mock
    private PostgresRepositoryJpa repository;

    @Mock
    private WorkTimeToWorkSessionMapper mapper;

    @InjectMocks
    private WorkSessionPostgresUpdate workSessionPostgresUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateWorkSession() {
        UUID workSessionId = UUID.randomUUID();
        String userId = UUID.randomUUID().toString();
        // Arrange
        WorkSession inputWorkSession = new WorkSession(
                userId, workSessionId,
                OffsetDateTime.parse("2024-11-15T00:00:00Z"),
                OffsetDateTime.parse("2024-11-15T08:00:00Z").toOffsetTime(),
                OffsetDateTime.parse("2024-11-15T12:00:00Z").toOffsetTime(),
                Duration.ofHours(4)
        );

        WorkTime mappedWorkTime = new WorkTime();
        mappedWorkTime.setUserId(userId);
        mappedWorkTime.setSessionId(workSessionId.toString());
        mappedWorkTime.setDate(OffsetDateTime.parse("2024-11-15T00:00:00Z"));
        mappedWorkTime.setStartTime(OffsetDateTime.parse("2024-11-15T08:00:00Z").toOffsetTime());
        mappedWorkTime.setEndTime(OffsetDateTime.parse("2024-11-15T12:00:00Z").toOffsetTime());
        mappedWorkTime.setDuration(Duration.ofHours(4));

        // Mock the mapping and repository behavior
        try (MockedStatic<WorkTimeToWorkSessionMapper> mockedMapper = mockStatic(WorkTimeToWorkSessionMapper.class)) {
            mockedMapper.when(() -> WorkTimeToWorkSessionMapper.mapToWorkTime(
                    anyString(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
            )).thenReturn(mappedWorkTime);

            // Act
            WorkSession result = workSessionPostgresUpdate.updateWorkSession(inputWorkSession);

            // Assert
            assertEquals(inputWorkSession, result);
            verify(repository, times(1)).save(mappedWorkTime);
        }
    }

}