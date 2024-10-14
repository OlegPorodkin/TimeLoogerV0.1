package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.input.CreateWorkSessionInputData;

/**
 *
 */
public interface CreationWorkSessionInputBoundary {
    /**
     *
     *
     * @param slicedInputData
     */
    void crateWorkSession(CreateWorkSessionInputData slicedInputData);
}
