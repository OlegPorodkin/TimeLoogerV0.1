package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataCreate;
import com.porodkin.timelogger.usecase.datastuct.input.WorkSessionInputDataUpdate;

/**
 * This interface defines the input boundary for creating a work session.
 */
public interface WorkSessionInputBoundaryWriting {

    /**
     * Creates a new work session based on the provided input data.
     *
     * @param slicedInputData the {@link WorkSessionInputDataCreate} object containing the necessary information to create a work session
     */
    void crateWorkSession(WorkSessionInputDataCreate slicedInputData);

    /**
     * Updates an existing work session with the provided input data.
     *
     * @param workSession the {@link WorkSessionInputDataUpdate} object containing the updated information for the work session
     */
    void updateWorkSession(WorkSessionInputDataUpdate workSession);
}
