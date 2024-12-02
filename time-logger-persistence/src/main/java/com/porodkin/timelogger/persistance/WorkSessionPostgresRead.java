package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.fabric.WorkSessionFabric;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessRead;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.Collection;
import java.util.stream.StreamSupport;

public class WorkSessionPostgresRead implements WorkSessionDataAccessRead {

    private final PostgresRepositoryJpa repository;

    public WorkSessionPostgresRead(PostgresRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Collection<WorkSession> findAllWorkSessions(String userId) {

        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                    .map(workTime -> WorkSessionFabric.create(
                            workTime.getUserId(),
                            workTime.getSessionId(),
                            workTime.getDate(),
                            workTime.getStartTime(),
                            workTime.getEndTime(),
                            workTime.getDuration()
                    ))
                    .toList();
    }

    @Override
    public WorkSession findByUserIdAndWorkSessionId(String userId, String sessionId) throws WorkSessionNotFoundException {

        WorkTime workTimeFromDb = repository.findByUserIdAndSessionId(userId, sessionId)
                .orElseThrow(() -> new WorkSessionNotFoundException(userId));

        return WorkSessionFabric.create(
                workTimeFromDb.getUserId(),
                workTimeFromDb.getSessionId(),
                workTimeFromDb.getDate(),
                workTimeFromDb.getStartTime(),
                workTimeFromDb.getEndTime(),
                workTimeFromDb.getDuration()
        );

    }

    @Override
    public WorkSession findWorkSessionByUserId(String userId){
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId is null or empty");
        }

        WorkTime workTimeFromDb = repository.findCurrentSessionByUserId(userId).orElseThrow(() -> new WorkSessionNotFoundException(userId));

        return WorkSessionFabric.create(
                workTimeFromDb.getUserId(),
                workTimeFromDb.getSessionId(),
                workTimeFromDb.getDate(),
                workTimeFromDb.getStartTime(),
                workTimeFromDb.getEndTime(),
                workTimeFromDb.getDuration()
        );
    }
}
