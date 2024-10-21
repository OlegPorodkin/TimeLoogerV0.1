package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.fabric.WorkSessionFabric;
import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.UpdateWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessBoundary;
import com.porodkin.timelogger.usecase.WritingWorkSessionInputBoundary;
import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.input.UpdateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import com.porodkin.timelogger.usecase.fabric.CreatedWorkSessionFactory;
import com.porodkin.timelogger.usecase.fabric.UpdatedWorkSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class WritingWorkSessionInteractor implements WritingWorkSessionInputBoundary {
    private static final Logger log = LoggerFactory.getLogger(WritingWorkSessionInteractor.class);

    public static final String SUCCESSFUL_CREATE_MESSAGE = "Work session successfully created.";
    public static final String SUCCESSFUL_UPDATE_MESSAGE = "Work session successfully updated.";
    public static final String FAILED_CREATE_MESSAGE = "Failed to create work session, Please fill the start date and time.";
    public static final String FAILED_UPDATE_MESSAGE = "Failed to update work session, Please fill end time.";

    private final WorkSessionDataAccessBoundary dataAccess;
    private final CreationWorkSessionOutputBoundary<?> addWorkedTimePresenter;
    private final UpdateWorkSessionOutputBoundary<?> updateWorkedTimePresenter;

    public WritingWorkSessionInteractor(
            WorkSessionDataAccessBoundary dataAccess,
            CreationWorkSessionOutputBoundary<?> addWorkedTimePresenter,
            UpdateWorkSessionOutputBoundary<?> updateWorkedTimePresenter
    ) {
        this.dataAccess = dataAccess;
        this.addWorkedTimePresenter = addWorkedTimePresenter;
        this.updateWorkedTimePresenter = updateWorkedTimePresenter;
    }

    @Override
    public void crateWorkSession(CreateWorkSessionInputData slicedInputData) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime workedTimeSlice = slicedInputData.getWorkSession();

        WorkSession workSession;
        try {
            workSession = WorkSessionFabric.create(uuid, workedTimeSlice);
        } catch (CreationWorkSessionException e) {
            log.error("Can't creation work session, date/time don't set", e);
            CreatedWorkSessionOutputData outputData = CreatedWorkSessionFactory.create(Boolean.FALSE, null, FAILED_CREATE_MESSAGE);
            addWorkedTimePresenter.presentCreatedWorkSession(outputData);
            return;
        }
        WorkSession savedWorkSession = dataAccess.saveWorkSession(workSession);

        log.info("Work session created: {}", uuid);
        CreatedWorkSessionOutputData outputData =
                CreatedWorkSessionFactory.create(Boolean.TRUE, savedWorkSession.getUuid().toString(), SUCCESSFUL_CREATE_MESSAGE);
        addWorkedTimePresenter.presentCreatedWorkSession(outputData);
    }

    @Override
    public void updateWorkSession(UpdateWorkSessionInputData workSession) {
        WorkSession workSessionFromDS = dataAccess
                .findByWorkSessionId(workSession.getSessionId().toString())
                .orElseThrow();

        try {
            workSessionFromDS.endWorkSession(workSession.getEndTime());
        } catch (EndWorkSessionException e) {
            log.error("Work session: {} failed to update. The end time is not set", workSessionFromDS.getUuid(), e);
            UpdateWorkSessionOutputData updateWorkSessionOutputData = UpdatedWorkSessionFactory.create(Boolean.FALSE, FAILED_UPDATE_MESSAGE);
            updateWorkedTimePresenter.presentUpdateWorkSession(updateWorkSessionOutputData);
            return;
        }

        dataAccess.updateWorkSession(workSessionFromDS);

        log.info("Work session updated: {}", workSessionFromDS.getUuid());
        UpdateWorkSessionOutputData updateWorkSessionOutputData = UpdatedWorkSessionFactory.create(Boolean.TRUE, SUCCESSFUL_UPDATE_MESSAGE);
        updateWorkedTimePresenter.presentUpdateWorkSession(updateWorkSessionOutputData);
    }
}
