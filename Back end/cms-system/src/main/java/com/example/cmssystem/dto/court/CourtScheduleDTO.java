package com.example.cmssystem.dto.court;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CourtScheduleDTO {
    private Long id;
    private Long courtId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status; // e.g. "AVAILABLE", "BOOKED"
}
