package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;

public interface CreationWorkSessionOutputBoundary<T> {
    void presentCreatedWorkSession(CreatedWorkSessionOutputData workedTime);
    T getData();
}
