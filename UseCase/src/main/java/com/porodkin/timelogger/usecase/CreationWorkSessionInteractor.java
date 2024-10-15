package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.CreationWorkSessionException;
import com.porodkin.timelogger.domain.fabric.WorkSessionFabric;
import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.fabric.CreatedWorkSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreationWorkSessionInteractor implements CreationWorkSessionInputBoundary {
    private static final Logger log = LoggerFactory.getLogger(CreationWorkSessionInteractor.class);

    public static final String SUCCESSFUL_MESSAGE = "Work session successfully created.";
    public static final String FAILED_MESSAGE = "Failed to create work session, Please fill the start date and time.";

    private final DataAccessBoundary dataAccess;
    private final CreationWorkSessionOutputBoundary<?> addWorkedTimePresenter;

    public CreationWorkSessionInteractor(DataAccessBoundary dataAccess, CreationWorkSessionOutputBoundary<?> addWorkedTimePresenter) {
        this.dataAccess = dataAccess;
        this.addWorkedTimePresenter = addWorkedTimePresenter;
    }

    @Override
    public void crateWorkSession(CreateWorkSessionInputData slicedInputData) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime workedTimeSlice = slicedInputData.getWorkSession();

        WorkSession workSession = null;
        try {
            workSession = WorkSessionFabric.create(uuid, workedTimeSlice);
        } catch (CreationWorkSessionException e) {
            log.warn("Can't creation work session, date/time don't set", e);
            CreatedWorkSessionOutputData outputData = CreatedWorkSessionFactory.create(Boolean.TRUE, null, FAILED_MESSAGE);
            addWorkedTimePresenter.presentCreatedWorkSession(outputData);
        }
        dataAccess.saveWorkTime(workSession);

        log.info("Work session created: {}", uuid);
        CreatedWorkSessionOutputData outputData = CreatedWorkSessionFactory.create(Boolean.TRUE, uuid.toString(), SUCCESSFUL_MESSAGE);
        addWorkedTimePresenter.presentCreatedWorkSession(outputData);
    }
}
