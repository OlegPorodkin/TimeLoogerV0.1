package com.porodkin.timelogger.application.configuratuion;

import com.porodkin.timelogger.controller.CreteWorkSessionRestPresenter;
import com.porodkin.timelogger.controller.UpdateWorkSessionRestPresenter;
import com.porodkin.timelogger.persistance.ImMemoryWorkTimePersist;
import com.porodkin.timelogger.usecase.*;
import com.porodkin.timelogger.usecase.interactor.WritingWorkSessionInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public WorkSessionDataAccessBoundary dataAccessBoundary() {
        return new ImMemoryWorkTimePersist();
    }

    @Bean
    public CreationWorkSessionOutputBoundary<?> creationWorkSessionOutputBoundary() {
        return new CreteWorkSessionRestPresenter();
    }

    @Bean
    public UpdateWorkSessionOutputBoundary<?> updateWorkSessionOutputBoundary() {
        return new UpdateWorkSessionRestPresenter();
    }

    @Bean
    public WritingWorkSessionInputBoundary creationWorkSessionInputBoundary(
            WorkSessionDataAccessBoundary workSessionDataAccessBoundary,
            CreationWorkSessionOutputBoundary<?> creationWorkSessionOutputBoundary,
            UpdateWorkSessionOutputBoundary<?> updateWorkSessionOutputBoundary
    ) {
        return new WritingWorkSessionInteractor(workSessionDataAccessBoundary, creationWorkSessionOutputBoundary, updateWorkSessionOutputBoundary);
    }
}
