package com.porodkin.timelogger.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class WorkTime {


    private Long id;

    private Long userId;
    private LocalDateTime date;


    private Integer overtimeDuration;
    private LocalTime workTime;
    private LocalTime overtime;
    private BigDecimal worktimePayment;
    private BigDecimal overtimePayment;
}
