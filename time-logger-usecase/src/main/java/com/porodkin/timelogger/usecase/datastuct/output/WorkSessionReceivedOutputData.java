package com.porodkin.timelogger.usecase.datastuct.output;

public record WorkSessionReceivedOutputData(Boolean success, String workSessionId, String message) {
}
