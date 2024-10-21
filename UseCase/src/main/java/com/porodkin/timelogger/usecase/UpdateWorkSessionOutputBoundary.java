package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;

public interface UpdateWorkSessionOutputBoundary<T> {
    void presentUpdateWorkSession(UpdateWorkSessionOutputData workSessionOutputData);
    T getData();
}
