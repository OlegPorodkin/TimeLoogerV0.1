package com.porodkin.timelogger.usecase.datastuct.persistence;

import java.time.LocalDate;
import java.time.LocalTime;

public class PersistenceWorkedTimeSlice {
    private String id;
    private LocalDate date;
    private LocalTime time;

    public PersistenceWorkedTimeSlice(String id, LocalDate date, LocalTime time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
