package com.porodkin.timelogger.persistance;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.persistance.entity.WorkTime;
import com.porodkin.timelogger.persistance.mappers.WorkTimeToWorkSessionMapper;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessSave;

public class WorkSessionPostgresSave implements WorkSessionDataAccessSave {

    private final PostgresRepositoryJpa repository;

    public WorkSessionPostgresSave(PostgresRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public WorkSession saveWorkSession(final WorkSession workSession) {

        WorkTime toDb = WorkTimeToWorkSessionMapper.mapToWorkTime(
                workSession.getUserId(),
                workSession.getUuid(),
                workSession.getDate(),
                workSession.getStartTime(),
                workSession.getEndTime(),
                workSession.getDuration()
        );

        repository.save(toDb);

        return workSession;
    }
}
