package com.porodkin.timelogger.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.porodkin.timelogger.persistance")
@EntityScan(basePackages = "com.porodkin.timelogger.persistance.entity")
@SpringBootApplication(
        scanBasePackages = {"com.porodkin.timelogger.*"}
)
public class TimeLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeLoggerApplication.class, args);
    }
}