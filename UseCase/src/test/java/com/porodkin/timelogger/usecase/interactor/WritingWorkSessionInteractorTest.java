package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.UpdateWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessBoundary;
import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.input.UpdateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WritingWorkSessionInteractorTest {

    @Mock
    private WorkSessionDataAccessBoundary dataAccess;
    @Mock
    private CreationWorkSessionOutputBoundary<?> addWorkedTimePresenter;
    @Mock
    private UpdateWorkSessionOutputBoundary<?> updateWorkedTimePresenter;

    private WritingWorkSessionInteractor interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interactor = new WritingWorkSessionInteractor(dataAccess, addWorkedTimePresenter, updateWorkedTimePresenter);
    }

    @Test
    void createWorkSessionSuccess() {
        CreateWorkSessionInputData inputData = mock(CreateWorkSessionInputData.class);
        when(inputData.getWorkSession()).thenReturn(LocalDateTime.now());

        WorkSession workSession = mock(WorkSession.class);
        when(workSession.getUuid()).thenReturn(UUID.randomUUID());
        when(dataAccess.saveWorkSession(any(WorkSession.class))).thenReturn(workSession);

        interactor.crateWorkSession(inputData);

        ArgumentCaptor<CreatedWorkSessionOutputData> captor = ArgumentCaptor.forClass(CreatedWorkSessionOutputData.class);
        verify(addWorkedTimePresenter).presentCreatedWorkSession(captor.capture());
        assertEquals(WritingWorkSessionInteractor.SUCCESSFUL_CREATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void createWorkSessionFailureDueToException() {
        CreateWorkSessionInputData inputData = mock(CreateWorkSessionInputData.class);
        when(inputData.getWorkSession()).thenReturn(null);

        interactor.crateWorkSession(inputData);

        ArgumentCaptor<CreatedWorkSessionOutputData> captor = ArgumentCaptor.forClass(CreatedWorkSessionOutputData.class);
        verify(addWorkedTimePresenter).presentCreatedWorkSession(captor.capture());
        assertEquals(WritingWorkSessionInteractor.FAILED_CREATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionSuccess() {
        UUID sessionId = UUID.randomUUID();
        UpdateWorkSessionInputData inputData = mock(UpdateWorkSessionInputData.class);
        when(inputData.getSessionId()).thenReturn(sessionId);
        when(inputData.getEndTime()).thenReturn(LocalTime.from(LocalDateTime.now()));

        WorkSession workSession = mock(WorkSession.class);
        when(workSession.getUuid()).thenReturn(sessionId);
        when(dataAccess.findByWorkSessionId(anyString())).thenReturn(Optional.of(workSession));

        interactor.updateWorkSession(inputData);

        ArgumentCaptor<UpdateWorkSessionOutputData> captor = ArgumentCaptor.forClass(UpdateWorkSessionOutputData.class);
        verify(updateWorkedTimePresenter).presentUpdateWorkSession(captor.capture());
        assertEquals(WritingWorkSessionInteractor.SUCCESSFUL_UPDATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionFailureDueToException() {
        UUID sessionId = UUID.randomUUID();
        UpdateWorkSessionInputData inputData = mock(UpdateWorkSessionInputData.class);
        when(inputData.getSessionId()).thenReturn(sessionId);
        when(inputData.getEndTime()).thenReturn(null);

        WorkSession workSession = new WorkSession(sessionId, LocalDateTime.now());
        when(dataAccess.findByWorkSessionId(eq(sessionId.toString()))).thenReturn(Optional.of(workSession));

        interactor.updateWorkSession(inputData);

        ArgumentCaptor<UpdateWorkSessionOutputData> captor = ArgumentCaptor.forClass(UpdateWorkSessionOutputData.class);
        verify(updateWorkedTimePresenter).presentUpdateWorkSession(captor.capture());
        assertEquals(WritingWorkSessionInteractor.FAILED_UPDATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UpdateWorkSessionInputData inputData = mock(UpdateWorkSessionInputData.class);
        when(inputData.getSessionId()).thenReturn(sessionId);

        when(dataAccess.findByWorkSessionId(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> interactor.updateWorkSession(inputData));
    }
}