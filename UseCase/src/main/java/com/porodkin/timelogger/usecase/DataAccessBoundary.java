package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.domain.WorkSession;

public interface DataAccessBoundary {
    WorkSession saveWorkTime(WorkSession workTime);
}
