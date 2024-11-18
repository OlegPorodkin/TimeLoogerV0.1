package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.usecase.*;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataCreate;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataUpdate;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WritingWorkSessionInteractorTest {

    @Mock
    private WorkSessionDataAccessSave workSessionDataAccessSave;
    @Mock
    private WorkSessionDataAccessUpdate workSessionDataAccessUpdate;

    @Mock
    private WorkSessionOutputBoundaryCreation<?> addWorkedTimePresenter;
    @Mock
    private WorkSessionOutputBoundaryUpdate<?> updateWorkedTimePresenter;

    @InjectMocks
    private WorkSessionInteractorWriting interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interactor = new WorkSessionInteractorWriting(
                workSessionDataAccessSave,
                workSessionDataAccessUpdate,
                addWorkedTimePresenter,
                updateWorkedTimePresenter
        );
    }

    @Test
    void createWorkSessionSuccess() {
        WorkSessionInputDataCreate inputData = mock(WorkSessionInputDataCreate.class);
        when(inputData.getWorkSession()).thenReturn(OffsetDateTime.now());

        WorkSession workSession = mock(WorkSession.class);
        when(workSession.getUuid()).thenReturn(UUID.randomUUID());
        when(workSessionDataAccessSave.saveWorkSession(any(WorkSession.class))).thenReturn(workSession);

        interactor.crateWorkSession(inputData);

        ArgumentCaptor<CreatedWorkSessionOutputData> captor = ArgumentCaptor.forClass(CreatedWorkSessionOutputData.class);
        verify(addWorkedTimePresenter).presentCreatedWorkSession(captor.capture());
        assertEquals(WorkSessionInteractorWriting.SUCCESSFUL_CREATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void createWorkSessionFailureDueToException() {
        WorkSessionInputDataCreate inputData = mock(WorkSessionInputDataCreate.class);
        when(inputData.getWorkSession()).thenReturn(null);

        interactor.crateWorkSession(inputData);

        ArgumentCaptor<CreatedWorkSessionOutputData> captor = ArgumentCaptor.forClass(CreatedWorkSessionOutputData.class);
        verify(addWorkedTimePresenter).presentCreatedWorkSession(captor.capture());
        assertEquals(WorkSessionInteractorWriting.FAILED_CREATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionSuccess() {
        String userId = UUID.randomUUID().toString();
        UUID sessionId = UUID.randomUUID();
        WorkSessionInputDataUpdate inputData = mock(WorkSessionInputDataUpdate.class);
        when(inputData.getUserId()).thenReturn(userId);
        when(inputData.getSessionId()).thenReturn(sessionId);
        when(inputData.getEndTime()).thenReturn(OffsetTime.now());

        WorkSession workSession = mock(WorkSession.class);
        when(workSession.getUserId()).thenReturn(userId);
        when(workSession.getUuid()).thenReturn(sessionId);

        when(workSessionDataAccessUpdate.updateWorkSession(any(WorkSession.class))).thenReturn(workSession);

        interactor.updateWorkSession(inputData);

        ArgumentCaptor<UpdateWorkSessionOutputData> captor = ArgumentCaptor.forClass(UpdateWorkSessionOutputData.class);
        verify(updateWorkedTimePresenter).presentUpdateWorkSession(captor.capture());
        assertEquals(WorkSessionInteractorWriting.SUCCESSFUL_UPDATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionFailureDueToException() {
        String userId = UUID.randomUUID().toString();
        UUID sessionId = UUID.randomUUID();
        WorkSessionInputDataUpdate inputData = mock(WorkSessionInputDataUpdate.class);
        when(inputData.getUserId()).thenReturn(userId);
        when(inputData.getSessionId()).thenReturn(sessionId);
        when(inputData.getEndTime()).thenReturn(null);

        WorkSession workSession = mock(WorkSession.class);
        when(workSession.getUserId()).thenReturn(userId);
        when(workSession.getUuid()).thenReturn(sessionId);

        when(workSessionDataAccessUpdate.updateWorkSession(any(WorkSession.class))).thenThrow(new EndWorkSessionException());

        interactor.updateWorkSession(inputData);

        ArgumentCaptor<UpdateWorkSessionOutputData> captor = ArgumentCaptor.forClass(UpdateWorkSessionOutputData.class);
        verify(updateWorkedTimePresenter).presentUpdateWorkSession(captor.capture());
        assertEquals(WorkSessionInteractorWriting.FAILED_UPDATE_MESSAGE, captor.getValue().getMessage());
    }

    @Test
    void updateWorkSessionNotFound() {
        UUID sessionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        WorkSessionInputDataUpdate inputData = mock(WorkSessionInputDataUpdate.class);
        when(inputData.getSessionId()).thenReturn(sessionId);
        when(inputData.getUserId()).thenReturn(userId.toString());

        when(workSessionDataAccessUpdate.updateWorkSession(any(WorkSession.class))).thenThrow(new WorkSessionNotFoundException(sessionId.toString()));

        interactor.updateWorkSession(inputData);

        ArgumentCaptor<UpdateWorkSessionOutputData> captor = ArgumentCaptor.forClass(UpdateWorkSessionOutputData.class);
        verify(updateWorkedTimePresenter).presentUpdateWorkSession(captor.capture());
        assertEquals(WorkSessionInteractorWriting.FAILED_UPDATE_MESSAGE_NOT_EXIST, captor.getValue().getMessage());
    }
}