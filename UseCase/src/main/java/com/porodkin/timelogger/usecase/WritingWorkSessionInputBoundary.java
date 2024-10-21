package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;
import com.porodkin.timelogger.usecase.datastuct.input.UpdateWorkSessionInputData;

/**
 * This interface defines the input boundary for creating a work session.
 */
public interface WritingWorkSessionInputBoundary {

    /**
     * Creates a new work session based on the provided input data.
     *
     * @param slicedInputData the {@link CreateWorkSessionInputData} object containing the necessary information to create a work session
     */
    void crateWorkSession(CreateWorkSessionInputData slicedInputData);

    /**
     * Updates an existing work session with the provided input data.
     *
     * @param workSession the {@link UpdateWorkSessionInputData} object containing the updated information for the work session
     */
    void updateWorkSession(UpdateWorkSessionInputData workSession);
}
