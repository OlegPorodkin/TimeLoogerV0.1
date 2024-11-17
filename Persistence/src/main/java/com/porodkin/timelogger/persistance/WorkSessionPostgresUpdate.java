package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.persistance.mappers.WorkTimeToWorkSessionMapper;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessUpdate;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

import java.util.Optional;
import java.util.UUID;

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

        fromDB.setEndTime(workSession.getEndTime());

        repository.save(fromDB);

        return workSession;
    }
}
