package com.badminton.booking.service;

import com.badminton.booking.entity.Court;
import com.badminton.booking.enums.CourtStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CourtService {
    List<Court> getAllCourts();
    Court getCourtById(Long id);
    List<Court> getAvailableCourts();
    List<Court> getAvailableCourts(LocalDateTime startTime, LocalDateTime endTime);
    Court createCourt(Court court);
    Court updateCourt(Long id, Court court);
    Court updateCourtStatus(Long id, CourtStatus status);
    void deleteCourt(Long id);
}