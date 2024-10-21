package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessBoundary;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.*;

public class ImMemoryWorkTimePersist implements WorkSessionDataAccessBoundary {

    private final Map<UUID, WorkSession> sessions = new HashMap<>();

    @Override
    public WorkSession saveWorkSession(WorkSession workTime) {
        sessions.put(workTime.getUuid(), workTime);
        return workTime;
    }

    @Override
    public Collection<WorkSession> findAllWorkSessions() {
        return sessions.values();
    }

    @Override
    public Optional<WorkSession> findByWorkSessionId(String id) throws WorkSessionNotFoundException {
        return Optional.of(sessions.get(UUID.fromString(id)));
    }

    @Override
    public WorkSession updateWorkSession(WorkSession workTime) {
        return sessions.put(workTime.getUuid(), workTime);
    }

    @Override
    public void deleteWorkSession(String id) {
        sessions.remove(UUID.fromString(id));
    }
}
