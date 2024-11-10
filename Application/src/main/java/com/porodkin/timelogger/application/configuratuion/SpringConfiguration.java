package com.porodkin.timelogger.application.configuratuion;

import com.porodkin.timelogger.presenters.WorkSessionRestPresenterCreate;
import com.porodkin.timelogger.presenters.WorkSessionRestPresenterUpdate;
import com.porodkin.timelogger.persistance.WorkTimeImMemoryPersist;
import com.porodkin.timelogger.usecase.*;
import com.porodkin.timelogger.usecase.interactor.WorkSessionInteractorWriting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public WorkTimeImMemoryPersist initWorkTimeImMemoryPersist() {
        return new WorkTimeImMemoryPersist();
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
            WorkTimeImMemoryPersist initWorkTimeImMemoryPersist,
            WorkSessionOutputBoundaryCreation<?> workSessionOutputBoundaryCreation,
            WorkSessionOutputBoundaryUpdate<?> workSessionOutputBoundaryUpdate
    ) {
        return new WorkSessionInteractorWriting(
                initWorkTimeImMemoryPersist,
                initWorkTimeImMemoryPersist,
                initWorkTimeImMemoryPersist,
                workSessionOutputBoundaryCreation,
                workSessionOutputBoundaryUpdate
        );
    }
}
