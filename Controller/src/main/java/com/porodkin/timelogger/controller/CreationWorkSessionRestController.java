package com.porodkin.timelogger.controller;

import com.porodkin.timelogger.usecase.CreationWorkSessionInputBoundary;
import com.porodkin.timelogger.usecase.CreationWorkSessionOutputBoundary;
import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/worksession")
public class CreationWorkSessionRestController {
    private final CreationWorkSessionInputBoundary inputBoundary;
    private final CreationWorkSessionOutputBoundary<ResponseEntity<CreatedWorkSessionOutputData>> outputBoundary;

    public CreationWorkSessionRestController(
            CreationWorkSessionInputBoundary inputBoundary,
            CreationWorkSessionOutputBoundary<ResponseEntity<CreatedWorkSessionOutputData>> outputBoundary
    ) {
        this.inputBoundary = inputBoundary;
        this.outputBoundary = outputBoundary;
    }

    @PostMapping("/create")
    public ResponseEntity<CreatedWorkSessionOutputData> createWorkSession(@RequestBody CreateWorkSessionInputData inputData) {
        inputBoundary.crateWorkSession(inputData);
        return outputBoundary.getData();
    }
}
