package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;

public interface WorkSessionDataAccessSave {
    /**
     * Saves the provided work session data.
     *
     * @param workTime the work session data to be saved
     * @return the saved {@link WorkSession} object
     */
    WorkSession saveWorkSession(WorkSession workTime);
}
