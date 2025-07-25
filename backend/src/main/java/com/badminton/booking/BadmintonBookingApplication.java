package com.badminton.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BadmintonBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BadmintonBookingApplication.class, args);
    }
}