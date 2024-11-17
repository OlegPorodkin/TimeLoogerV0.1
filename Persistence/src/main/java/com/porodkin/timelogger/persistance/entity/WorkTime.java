package com.porodkin.timelogger.persistance.entity;

import com.porodkin.timelogger.persistance.converters.DurationConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "work_time")
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String sessionId;
    private OffsetDateTime date;
    private OffsetTime startTime;
    private OffsetTime endTime;

    @Convert(converter = DurationConverter.class)
    private Duration duration;
}