package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.fabric.WorkSessionFabric;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessUpdate;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

public class WorkSessionPostgresUpdate implements WorkSessionDataAccessUpdate {

    private final PostgresRepositoryJpa repository;

    public WorkSessionPostgresUpdate(PostgresRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public WorkSession updateWorkSession(final WorkSession workSession) throws WorkSessionNotFoundException {

        WorkTime fromDB = repository
                .findByUserIdAndSessionId(workSession.getUserId(), workSession.getUuid().toString())
                .orElseThrow(() -> new WorkSessionNotFoundException(workSession.getUuid().toString()));

        if (workSession.getEndTime() != null) {
            fromDB.setEndTime(workSession.getEndTime());
        } else {
            throw new EndWorkSessionException("[Update work session] End time is null for work session " + workSession.getUuid().toString());
        }

        repository.save(fromDB);

        return WorkSessionFabric.create(
                fromDB.getUserId(),
                fromDB.getSessionId(),
                fromDB.getDate(),
                fromDB.getStartTime(),
                fromDB.getEndTime(),
                fromDB.getDuration()
        );
    }
}
