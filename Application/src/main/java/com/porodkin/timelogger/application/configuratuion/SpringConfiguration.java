package com.porodkin.timelogger.application.configuratuion;

import com.porodkin.timelogger.controller.presenters.WorkSessionRestPresenterCreate;
import com.porodkin.timelogger.controller.presenters.WorkSessionRestPresenterUpdate;
import com.porodkin.timelogger.persistance.*;
import com.porodkin.timelogger.usecase.*;
import com.porodkin.timelogger.usecase.interactor.WorkSessionInteractorWriting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public WorkSessionDataAccessRead workSessionDataAccessRead(PostgresRepositoryJpa postgresRepositoryJpa) {
        return new WorkSessionPostgresRead(postgresRepositoryJpa);
    }

    @Bean
    public WorkSessionDataAccessSave workSessionDataAccessSave(PostgresRepositoryJpa postgresRepositoryJpa) {
        return new WorkSessionPostgresSave(postgresRepositoryJpa);
    }

    @Bean
    public WorkSessionDataAccessUpdate workSessionDataAccessUpdate(PostgresRepositoryJpa postgresRepositoryJpa) {
        return new WorkSessionPostgresUpdate(postgresRepositoryJpa);
    }

    @Bean
    public WorkSessionOutputBoundaryCreation<?> creationWorkSessionOutputBoundary() {
        return new WorkSessionRestPresenterCreate();
    }

    @Bean
    public WorkSessionOutputBoundaryUpdate<?> updateWorkSessionOutputBoundary() {
        return new WorkSessionRestPresenterUpdate();
    }

    @Bean
    public WorkSessionInputBoundaryWriting creationWorkSessionInputBoundary(
            WorkSessionDataAccessRead workSessionDataAccessRead,
            WorkSessionDataAccessSave workSessionDataAccessSave,
            WorkSessionDataAccessUpdate workSessionDataAccessUpdate,
            WorkSessionOutputBoundaryCreation<?> workSessionOutputBoundaryCreation,
            WorkSessionOutputBoundaryUpdate<?> workSessionOutputBoundaryUpdate
    ) {
        return new WorkSessionInteractorWriting(
                workSessionDataAccessSave,
                workSessionDataAccessUpdate,
                workSessionDataAccessRead,
                workSessionOutputBoundaryCreation,
                workSessionOutputBoundaryUpdate
        );
    }
}
