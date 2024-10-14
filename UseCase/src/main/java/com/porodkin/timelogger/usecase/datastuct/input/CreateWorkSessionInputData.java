package com.porodkin.timelogger.usecase.datastuct.input;

import java.time.LocalDateTime;

public class CreateWorkSessionInputData {
    private LocalDateTime workSession;

    public CreateWorkSessionInputData() {
    }

    public CreateWorkSessionInputData(LocalDateTime workSession) {
        this.workSession = workSession;
    }

    public LocalDateTime getWorkSession() {
        return workSession;
    }

    public void setWorkSession(LocalDateTime workSession) {
        this.workSession = workSession;
    }
}
