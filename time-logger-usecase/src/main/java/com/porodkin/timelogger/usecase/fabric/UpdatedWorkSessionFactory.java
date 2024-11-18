package com.porodkin.timelogger.usecase.fabric;

import com.porodkin.timelogger.usecase.datastuct.output.UpdateWorkSessionOutputData;

public class UpdatedWorkSessionFactory {
    public static UpdateWorkSessionOutputData create(boolean success, String message) {
        return new UpdateWorkSessionOutputData(success, message);
    }
}
