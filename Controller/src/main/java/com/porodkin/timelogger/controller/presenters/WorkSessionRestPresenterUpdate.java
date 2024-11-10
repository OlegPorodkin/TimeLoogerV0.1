package com.porodkin.timelogger.controller.presenters;

import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryUpdate;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import org.springframework.http.ResponseEntity;

public class WorkSessionRestPresenterUpdate implements WorkSessionOutputBoundaryUpdate<ResponseEntity<UpdateWorkSessionOutputData>> {

    private ResponseEntity<UpdateWorkSessionOutputData> responseEntity;

    @Override
    public void presentUpdateWorkSession(UpdateWorkSessionOutputData workSessionOutputData) {
        if(workSessionOutputData.isSuccess()){
            responseEntity = ResponseEntity.ok(workSessionOutputData);
        } else {
            responseEntity = ResponseEntity.badRequest().body(workSessionOutputData);
        }
    }

    @Override
    public ResponseEntity<UpdateWorkSessionOutputData> getData() {
        return responseEntity;
    }
}
