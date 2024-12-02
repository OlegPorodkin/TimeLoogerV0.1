package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessRead;
import com.porodkin.timelogger.usecase.WorkSessionPresenter;
import com.porodkin.timelogger.usecase.datastuct.output.WorkSessionReceivedOutputData;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkSessionInteractorReaderTest {
    private WorkSessionDataAccessRead reader;
    private WorkSessionPresenter<WorkSessionReceivedOutputData> presenter;
    private WorkSessionInteractorReader interactor;

    private static final String EMPTY_USER_ID = WorkSessionInteractorReader.ErrorCode.EMPTY_USER_ID.getMessage();
    private static final String WORK_SESSION_NOT_FOUND = WorkSessionInteractorReader.ErrorCode.WORK_SESSION_NOT_FOUND.getMessage();

    @BeforeEach
    void setUp() {
        reader = mock(WorkSessionDataAccessRead.class);
        presenter = mock(WorkSessionPresenter.class);
        interactor = new WorkSessionInteractorReader(reader, presenter);
    }

    @Test
    void testGetCurrentWorkSessionByUserId_ValidUserId() {
        // Arrange
        String userId = "validUserId";
        WorkSession mockWorkSession = mock(WorkSession.class);
        when(mockWorkSession.getUuid()).thenReturn(UUID.randomUUID());
        when(reader.findWorkSessionByUserId(userId)).thenReturn(mockWorkSession);

        // Act
        interactor.getCurrentWorkSessionByUserId(userId);

        // Assert
        ArgumentCaptor<WorkSessionReceivedOutputData> captor = ArgumentCaptor.forClass(WorkSessionReceivedOutputData.class);
        verify(presenter).present(captor.capture());
        WorkSessionReceivedOutputData outputData = captor.getValue();

        assertTrue(outputData.success());
        assertNotNull(outputData.workSessionId());
        assertNull(outputData.message());
    }

    @Test
    void testGetCurrentWorkSessionByUserId_NullUserId() {
        // Arrange
        String userId = null;
        doThrow(new IllegalArgumentException()).when(reader).findWorkSessionByUserId(userId);

        // Act
        interactor.getCurrentWorkSessionByUserId(userId);

        // Assert
        ArgumentCaptor<WorkSessionReceivedOutputData> captor = ArgumentCaptor.forClass(WorkSessionReceivedOutputData.class);
        verify(presenter).present(captor.capture());
        WorkSessionReceivedOutputData outputData = captor.getValue();

        assertFalse(outputData.success());
        assertNull(outputData.workSessionId());
        assertEquals(EMPTY_USER_ID, outputData.message());
    }

    @Test
    void testGetCurrentWorkSessionByUserId_BlankUserId() {
        // Arrange
        String userId = " ";
        doThrow(new IllegalArgumentException()).when(reader).findWorkSessionByUserId(userId);

        // Act
        interactor.getCurrentWorkSessionByUserId(userId);

        // Assert
        ArgumentCaptor<WorkSessionReceivedOutputData> captor = ArgumentCaptor.forClass(WorkSessionReceivedOutputData.class);
        verify(presenter).present(captor.capture());
        WorkSessionReceivedOutputData outputData = captor.getValue();

        assertFalse(outputData.success());
        assertNull(outputData.workSessionId());
        assertEquals(EMPTY_USER_ID, outputData.message());
    }

    @Test
    void testGetCurrentWorkSessionByUserId_WorkSessionNotFound() {
        // Arrange
        String userId = "nonExistentUserId";
        doThrow(new WorkSessionNotFoundException("User not found")).when(reader).findWorkSessionByUserId(userId);

        // Act
        interactor.getCurrentWorkSessionByUserId(userId);

        // Assert
        ArgumentCaptor<WorkSessionReceivedOutputData> captor = ArgumentCaptor.forClass(WorkSessionReceivedOutputData.class);
        verify(presenter).present(captor.capture());
        WorkSessionReceivedOutputData outputData = captor.getValue();

        assertFalse(outputData.success());
        assertNull(outputData.workSessionId());
        assertEquals(WORK_SESSION_NOT_FOUND, outputData.message());
    }
}