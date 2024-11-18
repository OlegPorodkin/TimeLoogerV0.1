package com.porodkin.timelogger.controller;

import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryUpdate;
import com.porodkin.timelogger.usecase.WorkSessionInputBoundaryWriting;
import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryCreation;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataCreate;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataUpdate;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/work-session")
public class WorkSessionRestControllerWrite {

    private final WorkSessionInputBoundaryWriting inputBoundary;
    private final WorkSessionOutputBoundaryCreation<ResponseEntity<CreatedWorkSessionOutputData>> CreatOutputBoundary;
    private final WorkSessionOutputBoundaryUpdate<ResponseEntity<UpdateWorkSessionOutputData>> updateOutputBoundary;

    public WorkSessionRestControllerWrite(
            WorkSessionInputBoundaryWriting inputBoundary,
            WorkSessionOutputBoundaryCreation<ResponseEntity<CreatedWorkSessionOutputData>> CreatOutputBoundary,
            WorkSessionOutputBoundaryUpdate<ResponseEntity<UpdateWorkSessionOutputData>> updateOutputBoundary
    ) {
        this.inputBoundary = inputBoundary;
        this.CreatOutputBoundary = CreatOutputBoundary;
        this.updateOutputBoundary = updateOutputBoundary;
    }

    @PostMapping("/start")
    public ResponseEntity<CreatedWorkSessionOutputData> createWorkSession(@RequestBody WorkSessionInputDataCreate inputData) {
        inputBoundary.crateWorkSession(inputData);
        return CreatOutputBoundary.getData();
    }

    @PutMapping("/end")
    public ResponseEntity<UpdateWorkSessionOutputData> endWorkSession(@RequestBody WorkSessionInputDataUpdate inputData) {
        inputBoundary.updateWorkSession(inputData);
        return updateOutputBoundary.getData();
    }
}
