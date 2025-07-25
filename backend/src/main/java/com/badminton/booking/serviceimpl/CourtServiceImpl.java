package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.Court;
import com.badminton.booking.enums.CourtStatus;
import com.badminton.booking.repository.CourtRepository;
import com.badminton.booking.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourtServiceImpl implements CourtService {

    @Autowired
    private CourtRepository courtRepository;

    @Override
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    @Override
    public Court getCourtById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found"));
    }

    @Override
    public List<Court> getAvailableCourts() {
        return courtRepository.findByStatus(CourtStatus.AVAILABLE);
    }

    @Override
    public List<Court> getAvailableCourts(LocalDateTime startTime, LocalDateTime endTime) {
        return courtRepository.findAvailableCourts(startTime, endTime);
    }

    @Override
    public Court createCourt(Court court) {
        return courtRepository.save(court);
    }

    @Override
    public Court updateCourt(Long id, Court court) {
        Court existingCourt = getCourtById(id);
        existingCourt.setName(court.getName());
        existingCourt.setDescription(court.getDescription());
        existingCourt.setHourlyRate(court.getHourlyRate());
        return courtRepository.save(existingCourt);
    }

    @Override
    public Court updateCourtStatus(Long id, CourtStatus status) {
        Court court = getCourtById(id);
        court.setStatus(status);
        return courtRepository.save(court);
    }

    @Override
    public void deleteCourt(Long id) {
        courtRepository.deleteById(id);
    }
}