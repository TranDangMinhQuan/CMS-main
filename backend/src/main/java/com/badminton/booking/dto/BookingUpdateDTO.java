package com.badminton.booking.dto;

import com.badminton.booking.entity.Booking;
import java.time.LocalDateTime;

public class BookingUpdateDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
    private Booking.BookingStatus status;

    // Constructors
    public BookingUpdateDTO() {}

    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Booking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(Booking.BookingStatus status) {
        this.status = status;
    }
}