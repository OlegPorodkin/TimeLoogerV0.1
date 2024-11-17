package com.porodkin.timelogger.persistance.mappers;

import com.porodkin.timelogger.domain.WorkSession;
import com.porodkin.timelogger.persistance.entity.WorkTime;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.UUID;

public class WorkTimeToWorkSessionMapper {

    public static WorkTime mapToWorkTime(
            String userId, UUID uuid, OffsetDateTime date, OffsetTime startTime, OffsetTime endTime, Duration duration
    ) {
        WorkTime workTime = new WorkTime();
        workTime.setUserId(userId);
        workTime.setSessionId(uuid.toString());
        workTime.setDate(date);
        workTime.setStartTime(startTime);
        workTime.setEndTime(endTime);
        workTime.setDuration(duration);
        return workTime;
    }

    public static WorkSession mapToWorkSession(
            String userId, UUID uuid, OffsetDateTime date, OffsetTime startTime, OffsetTime endTime, Duration duration
    ) {
        return new WorkSession(userId, uuid, date, startTime, endTime, duration);
    }
}
