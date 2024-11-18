package com.porodkin.timelogger.domain;

import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.WorkedTimeDurationException;

import java.time.*;
import java.util.UUID;

/**
 * WorkedTime is a domain model thai is responsible for started,
 * finished work session and calculating working time
 */
public class WorkSession {

    public final static String END_WORK_SESSION_EXCEPTION = "The endTime in work session could not be a null.";
    public final static String CALCULATE_END_WORK_SESSION_EXCEPTION = END_WORK_SESSION_EXCEPTION + " First, end of session";
    public final static String CALCULATE_WORK_TIME_DURATION_EXCEPTION = "Work time duration cannot be less than 1h";

    private String userId;
    private UUID uuid;
    private OffsetDateTime date;
    private OffsetTime startTime;
    private OffsetTime endTime;
    private Duration duration;

    /**
     * Started new work session and started it
     *
     * @param uuid unique identifier of work session
     * @param startTime time and date of started work session
     */
    public WorkSession(String userId, UUID uuid, OffsetDateTime startTime) {
        this.userId = userId;
        this.uuid = uuid;
        this.startTime = startTime.toOffsetTime();
        this.date = startTime;
    }

    public WorkSession(String userId, UUID uuid, OffsetDateTime date, OffsetTime startTime, OffsetTime endTime, Duration duration) {
        this.userId = userId;
        this.uuid = uuid;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    /**
     * Finished work session</br>
     * The parameter endTime could not be a null otherwise it will be throw exception
     *
     * @param endTime in what time work session is over
     * @throws EndWorkSessionException when ending time is null;
     */
    public void endWorkSession(OffsetTime endTime) {
        if (endTime == null) {
            throw new EndWorkSessionException(END_WORK_SESSION_EXCEPTION);
        }
        this.endTime = endTime;
    }

    /**
     * Calculates the time worked between start and end time period.
     *
     * @throws WorkedTimeDurationException if the duration between start and end is less than 1 hour.
     * @throws EndWorkSessionException
     */
    public void calculateWorkDuration() {
        if (endTime == null) {
            throw new EndWorkSessionException(CALCULATE_END_WORK_SESSION_EXCEPTION);
        }

        Duration workHours = Duration.between(startTime, endTime);

        long hours = workHours.toHours();

        if (hours < 1) {
            throw new WorkedTimeDurationException(CALCULATE_WORK_TIME_DURATION_EXCEPTION);
        }

        this.duration = workHours;
    }

    public String getUserId() {
        return userId;
    }
    public UUID getUuid() {
        return uuid;
    }
    public OffsetDateTime getDate() {return date;}
    public OffsetTime getStartTime() {
        return startTime;
    }
    public OffsetTime getEndTime() {
        return endTime;
    }
    public Duration getDuration() {
        return duration;
    }
}
