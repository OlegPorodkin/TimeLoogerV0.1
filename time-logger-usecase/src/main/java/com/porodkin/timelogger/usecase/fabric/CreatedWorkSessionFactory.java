package com.porodkin.timelogger.usecase.fabric;

import com.porodkin.timelogger.usecase.datastuct.output.CreatedWorkSessionOutputData;

public class CreatedWorkSessionFactory {

    public static CreatedWorkSessionOutputData create(boolean success, String uuid, String message) {
        return new CreatedWorkSessionOutputData(success, uuid, message);
    }
}
