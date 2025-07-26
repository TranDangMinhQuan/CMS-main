package com.example.cmssystem.entity.court;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "court_schedules")
@Data
@NoArgsConstructor
public class CourtSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status; // e.g. "AVAILABLE", "BOOKED"
}
