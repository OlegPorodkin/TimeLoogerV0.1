package com.porodkin.timelogger.controller;

import com.porodkin.timelogger.usecase.WorkSessionActiveReading;
import com.porodkin.timelogger.usecase.WorkSessionOutputBoundaryReader;
import com.porodkin.timelogger.usecase.datastuct.output.WorkSessionReceivedOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/work-session")
public class WorkSessionRestControllerRead {

    private final WorkSessionActiveReading inputBoundary;
    private final WorkSessionOutputBoundaryReader<ResponseEntity<WorkSessionReceivedOutputData>> presenter;

    public WorkSessionRestControllerRead(
            WorkSessionActiveReading inputBoundary,
            WorkSessionOutputBoundaryReader<ResponseEntity<WorkSessionReceivedOutputData>> outputBoundaryCreation
    ) {
        this.inputBoundary = inputBoundary;
        this.presenter = outputBoundaryCreation;
    }

    @GetMapping("/{userId}/current")
    public ResponseEntity<WorkSessionReceivedOutputData> getCurrentSessionForUser(@PathVariable("userId") Long userId) {
        inputBoundary.getCurrentWorkSessionByUserId(String.valueOf(userId));
        return presenter.getData();
    }
}
