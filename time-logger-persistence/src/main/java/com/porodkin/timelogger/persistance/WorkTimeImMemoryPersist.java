package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessRead;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessSave;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessUpdate;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.*;

public class WorkTimeImMemoryPersist implements WorkSessionDataAccessRead, WorkSessionDataAccessSave, WorkSessionDataAccessUpdate {

    private final Map<UUID, WorkSession> sessions = new HashMap<>();

    @Override
    public WorkSession saveWorkSession(WorkSession workTime) {
        sessions.put(workTime.getUuid(), workTime);
        return workTime;
    }

    @Override
    public Collection<WorkSession> findAllWorkSessions(String userId) {
        return sessions.values();
    }

    @Override
    public WorkSession findByUserIdAndWorkSessionId(String userId, String sessionId) throws WorkSessionNotFoundException {
        return sessions.get(UUID.fromString(sessionId));
    }

    @Override
    public WorkSession updateWorkSession(WorkSession workTime) throws WorkSessionNotFoundException {
        return sessions.put(workTime.getUuid(), workTime);
    }

    @Override
    public WorkSession findWorkSessionByUserId(String userId) {
        return null;
    }
}
