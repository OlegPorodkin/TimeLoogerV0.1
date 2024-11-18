package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.usecase.exceptions.WorkSessionNotFoundException;

public interface WorkSessionDataAccessUpdate {
    /**
     * Updates an existing work session in the data store.
     *
     * @param workSession the {@link WorkSession} object containing updated information
     * @return the updated {@link WorkSession} object, or null if the update was not successful
     */
    WorkSession updateWorkSession(WorkSession workSession) throws WorkSessionNotFoundException;
}
