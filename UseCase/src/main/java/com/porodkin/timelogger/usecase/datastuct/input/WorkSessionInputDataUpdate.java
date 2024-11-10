package com.porodkin.timelogger.usecase.datastuct.input;

import java.time.OffsetTime;
import java.util.UUID;

public class WorkSessionInputDataUpdate {

    private String userId;
    private UUID sessionId;
    private OffsetTime endTime;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public UUID getSessionId() {
        return sessionId;
    }
    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }
    public OffsetTime getEndTime() {
        return endTime;
    }
    public void setEndTime(OffsetTime endTime) {
        this.endTime = endTime;
    }
}
