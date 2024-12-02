package com.porodkin.timelogger.usecase.interactor;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.WorkSessionActiveReading;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessRead;
import com.porodkin.timelogger.usecase.WorkSessionPresenter;
import com.porodkin.timelogger.usecase.datastuct.output.WorkSessionReceivedOutputData;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

public class WorkSessionInteractorReader implements WorkSessionActiveReading {

    private final WorkSessionDataAccessRead reader;
    private final WorkSessionPresenter<WorkSessionReceivedOutputData> presenter;

    public WorkSessionInteractorReader(WorkSessionDataAccessRead reader, WorkSessionPresenter<WorkSessionReceivedOutputData> presenter) {
        this.reader = reader;
        this.presenter = presenter;
    }

    @Override
    public void getCurrentWorkSessionByUserId(String userId) {
        WorkSession workSessionByUserId;

        try {
            workSessionByUserId = reader.findWorkSessionByUserId(userId);
        } catch (IllegalArgumentException e) {
            presenter.present(new WorkSessionReceivedOutputData(false, null, ErrorCode.EMPTY_USER_ID.getMessage()));
            return;
        } catch (WorkSessionNotFoundException e) {
            presenter.present(new WorkSessionReceivedOutputData(false, null, ErrorCode.WORK_SESSION_NOT_FOUND.getMessage()));
            return;
        }

        presenter.present(new WorkSessionReceivedOutputData(true, workSessionByUserId.getUuid().toString(), null));
    }

    public enum ErrorCode {
        EMPTY_USER_ID("You are need to fill user id first."),
        WORK_SESSION_NOT_FOUND("Work session not found by user id.");

        private String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
