package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.fabric.WorkSessionFabric;
import com.porodkin.timelogger.usecase.*;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataCreate;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataUpdate;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;
import com.porodkin.timelogger.usecase.fabric.CreatedWorkSessionFactory;
import com.porodkin.timelogger.usecase.fabric.UpdatedWorkSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.UUID;

public class WorkSessionInteractorWriting implements WorkSessionInputBoundaryWriting {

    private static final Logger log = LoggerFactory.getLogger(WorkSessionInteractorWriting.class);

    public static final String SUCCESSFUL_CREATE_MESSAGE = "Work session successfully created.";
    public static final String SUCCESSFUL_UPDATE_MESSAGE = "Work session successfully updated.";
    public static final String FAILED_CREATE_MESSAGE = "Failed to create work session, Please fill the start date and time.";
    public static final String FAILED_UPDATE_MESSAGE_NOT_EXIST = "Failed to update work session, Please make sure work session id is correct";
    public static final String FAILED_UPDATE_MESSAGE = "Failed to update work session, Please make sure end time is filled.";

    private final WorkSessionDataAccessSave workSessionDataAccessSave;
    private final WorkSessionDataAccessUpdate workSessionDataAccessUpdate;
    private final WorkSessionDataAccessRead workSessionDataAccessRead;

    private final WorkSessionOutputBoundaryCreation<?> addWorkedTimePresenter;
    private final WorkSessionOutputBoundaryUpdate<?> updateWorkedTimePresenter;

    public WorkSessionInteractorWriting(
            WorkSessionDataAccessSave workSessionDataAccessSave,
            WorkSessionDataAccessUpdate workSessionDataAccessUpdate,
            WorkSessionDataAccessRead workSessionDataAccessRead,
            WorkSessionOutputBoundaryCreation<?> addWorkedTimePresenter,
            WorkSessionOutputBoundaryUpdate<?> updateWorkedTimePresenter
    ) {
        this.workSessionDataAccessSave = workSessionDataAccessSave;
        this.workSessionDataAccessUpdate = workSessionDataAccessUpdate;
        this.workSessionDataAccessRead = workSessionDataAccessRead;
        this.addWorkedTimePresenter = addWorkedTimePresenter;
        this.updateWorkedTimePresenter = updateWorkedTimePresenter;
    }

    @Override
    public void crateWorkSession(WorkSessionInputDataCreate slicedInputData) {
        String userId = slicedInputData.getUserId();
        UUID uuid = UUID.randomUUID();
        OffsetDateTime workedTimeSlice = slicedInputData.getWorkSession();

        WorkSession workSession;
        try {
            workSession = WorkSessionFabric.create(userId, uuid, workedTimeSlice);
        } catch (CreationWorkSessionException e) {
            log.error("Can't creation work session, date/time don't set", e);
            CreatedWorkSessionOutputData outputData = CreatedWorkSessionFactory.create(Boolean.FALSE, null, FAILED_CREATE_MESSAGE);
            addWorkedTimePresenter.presentCreatedWorkSession(outputData);
            return;
        }
        WorkSession savedWorkSession = workSessionDataAccessSave.saveWorkSession(workSession);

        log.info("Work session created: {}", uuid);
        CreatedWorkSessionOutputData outputData =
                CreatedWorkSessionFactory.create(Boolean.TRUE, savedWorkSession.getUuid().toString(), SUCCESSFUL_CREATE_MESSAGE);
        addWorkedTimePresenter.presentCreatedWorkSession(outputData);
    }

    @Override
    public void updateWorkSession(WorkSessionInputDataUpdate workSession) {
        WorkSession workSessionFromDS;
        String userId = workSession.getUserId();
        String sessionId = workSession.getSessionId().toString();
        OffsetTime endTime = workSession.getEndTime();

        try {
            WorkSession updateEndTime = WorkSessionFabric.create(userId, sessionId, endTime);

            workSessionFromDS = workSessionDataAccessUpdate.updateWorkSession(updateEndTime);

        } catch (WorkSessionNotFoundException e) {
            log.error("Work session: {} failed to update.The work session is not exist", workSession.getSessionId(), e);
            UpdateWorkSessionOutputData updateWorkSessionOutputData = UpdatedWorkSessionFactory.create(Boolean.FALSE, FAILED_UPDATE_MESSAGE_NOT_EXIST);
            updateWorkedTimePresenter.presentUpdateWorkSession(updateWorkSessionOutputData);
            return;
        } catch (EndWorkSessionException e) {
            log.error("Work session: {} failed to update.The work session end time is not set", workSession.getSessionId(), e);
            UpdateWorkSessionOutputData updateWorkSessionOutputData = UpdatedWorkSessionFactory.create(Boolean.FALSE, FAILED_UPDATE_MESSAGE);
            updateWorkedTimePresenter.presentUpdateWorkSession(updateWorkSessionOutputData);
            return;
        }

        log.info("Work session updated: {}", workSessionFromDS.getUuid());
        UpdateWorkSessionOutputData updateWorkSessionOutputData = UpdatedWorkSessionFactory.create(Boolean.TRUE, SUCCESSFUL_UPDATE_MESSAGE);
        updateWorkedTimePresenter.presentUpdateWorkSession(updateWorkSessionOutputData);
    }
}
