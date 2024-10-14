package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.DataAccessBoundary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImMemoryWorkTimePersist implements DataAccessBoundary {

    private final Map<UUID, WorkSession> sessions = new HashMap<>();

    @Override
    public WorkSession saveWorkTime(WorkSession workTime) {
        return sessions.put(workTime.getUuid(), workTime);
    }
}
