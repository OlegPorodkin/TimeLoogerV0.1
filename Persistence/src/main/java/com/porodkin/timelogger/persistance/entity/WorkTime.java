package com.porodkin.timelogger.persistance.entity;

import com.porodkin.timelogger.persistance.converters.DurationConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.*;

@Data
@Entity
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String sessionId;
    private OffsetDateTime date;
    private String userOffset;
    private OffsetTime startTime;
    private OffsetTime endTime;

    @Convert(converter = DurationConverter.class)
    private Duration duration;
}