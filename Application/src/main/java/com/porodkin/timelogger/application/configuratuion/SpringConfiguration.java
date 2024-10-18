package com.porodkin.timelogger.application.configuratuion;

import com.porodkin.timelogger.controller.CreteWorkSessionRestPresenter;
import com.porodkin.timelogger.persistance.ImMemoryWorkTimePersist;
import com.porodkin.timelogger.usecase.CreationWorkSessionInputBoundary;
import com.porodkin.timelogger.usecase.CreationWorkSessionInteractor;
import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.WorkSessionDataAccessBoundary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public WorkSessionDataAccessBoundary dataAccessBoundary() {
        return new ImMemoryWorkTimePersist();
    }

    @Bean
    public CreationWorkSessionOutputBoundary creationWorkSessionOutputBoundary() {
        return new CreteWorkSessionRestPresenter();
    }

    @Bean
    public CreationWorkSessionInputBoundary creationWorkSessionInputBoundary(WorkSessionDataAccessBoundary workSessionDataAccessBoundary, CreationWorkSessionOutputBoundary creationWorkSessionOutputBoundary) {
        return new CreationWorkSessionInteractor(workSessionDataAccessBoundary, creationWorkSessionOutputBoundary);
    }
}
