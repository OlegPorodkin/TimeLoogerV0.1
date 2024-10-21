package com.porodkin.timelogger.controller;

import com.porodkin.timelogger.usecase.UpdateWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.WritingWorkSessionInputBoundary;
import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.input.UpdateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/worksession")
public class WritingWorkSessionRestController {
    private final WritingWorkSessionInputBoundary inputBoundary;
    private final CreationWorkSessionOutputBoundary<ResponseEntity<CreatedWorkSessionOutputData>> CreatOutputBoundary;
    private final UpdateWorkSessionOutputBoundary<ResponseEntity<UpdateWorkSessionOutputData>> updateOutputBoundary;

    public WritingWorkSessionRestController(
            WritingWorkSessionInputBoundary inputBoundary,
            CreationWorkSessionOutputBoundary<ResponseEntity<CreatedWorkSessionOutputData>> CreatOutputBoundary,
            UpdateWorkSessionOutputBoundary<ResponseEntity<UpdateWorkSessionOutputData>> updateOutputBoundary
    ) {
        this.inputBoundary = inputBoundary;
        this.CreatOutputBoundary = CreatOutputBoundary;
        this.updateOutputBoundary = updateOutputBoundary;
    }

    @PostMapping("/create")
    public ResponseEntity<CreatedWorkSessionOutputData> createWorkSession(@RequestBody CreateWorkSessionInputData inputData) {
        inputBoundary.crateWorkSession(inputData);
        return CreatOutputBoundary.getData();
    }

    @PutMapping("/end")
    public ResponseEntity<UpdateWorkSessionOutputData> endWorkSession(@RequestBody UpdateWorkSessionInputData inputData) {
        inputBoundary.updateWorkSession(inputData);
        return updateOutputBoundary.getData();
    }
}
