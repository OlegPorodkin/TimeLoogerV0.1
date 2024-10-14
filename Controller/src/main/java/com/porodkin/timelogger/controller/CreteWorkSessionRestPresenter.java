package com.porodkin.timelogger.controller;

import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import org.springframework.http.ResponseEntity;

public class CreteWorkSessionRestPresenter implements CreationWorkSessionOutputBoundary<ResponseEntity<CreatedWorkSessionOutputData>> {

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
