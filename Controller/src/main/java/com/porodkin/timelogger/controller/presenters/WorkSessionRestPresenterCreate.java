package com.porodkin.timelogger.controller.presenters;

import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryCreation;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import org.springframework.http.ResponseEntity;

public class WorkSessionRestPresenterCreate implements WorkSessionOutputBoundaryCreation<ResponseEntity<CreatedWorkSessionOutputData>> {

    private ResponseEntity<CreatedWorkSessionOutputData> responseEntity;

    @Override
    public void presentCreatedWorkSession(CreatedWorkSessionOutputData workedTime) {
        if (workedTime.isSuccess()) {
            this.responseEntity = ResponseEntity.ok(workedTime);
        } else {
            this.responseEntity = ResponseEntity.badRequest().body(workedTime);
        }
    }

    @Override
    public ResponseEntity<CreatedWorkSessionOutputData> getData() {
        return responseEntity;
    }
}
