package com.porodkin.timelogger.usecase.datastuct.input;

import java.time.OffsetDateTime;

public class WorkSessionInputDataCreate {

    private String userId;
    private OffsetDateTime workSession;

    public OffsetDateTime getWorkSession() {
        return workSession;
    }
    public void setWorkSession(OffsetDateTime workSession) {
        this.workSession = workSession;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
