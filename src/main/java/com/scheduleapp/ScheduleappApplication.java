package com.scheduleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scheduleapp")
public class ScheduleappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleappApplication.class, args);
    }
}