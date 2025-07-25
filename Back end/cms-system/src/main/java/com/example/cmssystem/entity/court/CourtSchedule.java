package com.example.cmssystem.entity.court;

import com.example.cmssystem.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Court_Schedule")
@Data
public class CourtSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Court_Id", nullable = false)
    private Court court;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "Start_Time", nullable = false)
    private LocalTime startTime;

    @Column(name = "End_Time", nullable = false)
    private LocalTime endTime;

    @Column(name = "Is_Available")
    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
