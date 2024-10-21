package com.porodkin.timelogger.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
        scanBasePackages = {"com.porodkin.timelogger.controller", "com.porodkin.timelogger.application"}
)
public class TimeLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeLoggerApplication.class, args);
    }
}