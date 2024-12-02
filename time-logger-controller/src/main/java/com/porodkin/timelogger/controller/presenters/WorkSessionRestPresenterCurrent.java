package com.porodkin.timelogger.controller.presenters;

import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryReader;
import com.porodkin.timelogger.usecase.datastuct.output.WorkSessionReceivedOutputData;
import com.porodkin.timelogger.usecase.interactor.WorkSessionInteractorReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WorkSessionRestPresenterCurrent implements WorkSessionOutputBoundaryReader<ResponseEntity<WorkSessionReceivedOutputData>> {

    private ResponseEntity<WorkSessionReceivedOutputData> response;

    @Override
    public ResponseEntity<WorkSessionReceivedOutputData> getData() {
        return response;
    }

    @Override
    public void present(WorkSessionReceivedOutputData workSessionReceivedOutputData) {
        if (workSessionReceivedOutputData.success()) {
            response = new ResponseEntity<>(workSessionReceivedOutputData, HttpStatus.OK);
        } else {
            if (workSessionReceivedOutputData.message().equals(WorkSessionInteractorReader.ErrorCode.EMPTY_USER_ID.getMessage())){
                response = new ResponseEntity<>(workSessionReceivedOutputData, HttpStatus.BAD_REQUEST);
            } else if (workSessionReceivedOutputData.message().equals(WorkSessionInteractorReader.ErrorCode.WORK_SESSION_NOT_FOUND.getMessage())){
                response = new ResponseEntity<>(workSessionReceivedOutputData, HttpStatus.NOT_FOUND);
            }
        }
    }
}
