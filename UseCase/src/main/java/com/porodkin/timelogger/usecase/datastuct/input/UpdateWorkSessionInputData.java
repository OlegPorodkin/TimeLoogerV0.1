package com.porodkin.timelogger.usecase.datastuct.input;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class UpdateWorkSessionInputData {
    private UUID sessionId;
    private LocalTime endTime;

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
