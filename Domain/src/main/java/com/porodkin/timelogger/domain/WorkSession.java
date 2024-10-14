package com.porodkin.timelogger.domain;

import com.porodkin.timelogger.domain.exceptions.EndWorkSessionException;
import com.porodkin.timelogger.domain.exceptions.WorkedTimeDurationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * WorkedTime is a domain model thai is responsible for started,
 * finished work session and calculating working time
 */
public class WorkSession {

    public final static String END_WORK_SESSION_EXCEPTION = "The endTime in work session could not be a null.";
    public final static String CALCULATE_END_WORK_SESSION_EXCEPTION = END_WORK_SESSION_EXCEPTION + " First, end of session";
    public final static String CALCULATE_WORK_TIME_DURATION_EXCEPTION = "Work time duration cannot be less than 1h";

    private UUID uuid;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime duration;

    /**
     * Started new work session and started it
     *
     * @param uuid unique identifier of work session
     * @param startTime time and date of started work session
     */
    public WorkSession(UUID uuid, LocalDateTime startTime) {
        this.uuid = uuid;
        this.startTime = startTime.toLocalTime();
        this.date = startTime.toLocalDate();
    }

    /**
     * Finished work session</br>
     * The parameter endTime could not be a null otherwise it will be throw exception
     *
     * @param endTime in what time work session is over
     * @throws EndWorkSessionException when ending time is null;
     */
    public void endWorkSession(LocalTime endTime) {
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

        long minutes = workHours.toMinutes() % 60;

        this.duration = LocalTime.of((int) hours, (int) minutes);
    }

    public UUID getUuid() {
        return uuid;
    }
    public LocalDate getDate() {return date;}
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public LocalTime getDuration() {
        return duration;
    }
}
