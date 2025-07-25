package com.badminton.booking.service;

import com.badminton.booking.entity.Court;
import com.badminton.booking.dto.CourtCreateDTO;
import com.badminton.booking.dto.CourtUpdateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourtService {
    Court createCourt(CourtCreateDTO courtCreateDTO);
    Court updateCourt(Long id, CourtUpdateDTO courtUpdateDTO);
    Court updateCourtStatus(Long id, Court.CourtStatus status);
    
    Optional<Court> getCourtById(Long id);
    Optional<Court> getCourtByName(String courtName);
    List<Court> getAllCourts();
    List<Court> getCourtsByStatus(Court.CourtStatus status);
    List<Court> getAvailableCourts(LocalDateTime startTime, LocalDateTime endTime);
    List<Court> searchCourtsByName(String name);
    
    Double getCourtAverageRating(Long courtId);
    void deleteCourt(Long id);
}