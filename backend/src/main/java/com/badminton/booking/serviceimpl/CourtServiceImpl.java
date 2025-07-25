package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.Court;
import com.badminton.booking.dto.CourtCreateDTO;
import com.badminton.booking.dto.CourtUpdateDTO;
import com.badminton.booking.repository.CourtRepository;
import com.badminton.booking.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    @Autowired
    public CourtServiceImpl(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @Override
    public Court createCourt(CourtCreateDTO courtCreateDTO) {
        if (courtRepository.findByCourtName(courtCreateDTO.getCourtName()).isPresent()) {
            throw new RuntimeException("Court name already exists!");
        }

        Court court = new Court();
        court.setCourtName(courtCreateDTO.getCourtName());
        court.setDescription(courtCreateDTO.getDescription());
        court.setPricePerHour(courtCreateDTO.getPricePerHour());
        court.setImageUrl(courtCreateDTO.getImageUrl());

        return courtRepository.save(court);
    }

    @Override
    public Court updateCourt(Long id, CourtUpdateDTO courtUpdateDTO) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with id: " + id));

        if (courtUpdateDTO.getCourtName() != null) {
            court.setCourtName(courtUpdateDTO.getCourtName());
        }
        if (courtUpdateDTO.getDescription() != null) {
            court.setDescription(courtUpdateDTO.getDescription());
        }
        if (courtUpdateDTO.getPricePerHour() != null) {
            court.setPricePerHour(courtUpdateDTO.getPricePerHour());
        }
        if (courtUpdateDTO.getStatus() != null) {
            court.setStatus(courtUpdateDTO.getStatus());
        }
        if (courtUpdateDTO.getImageUrl() != null) {
            court.setImageUrl(courtUpdateDTO.getImageUrl());
        }

        return courtRepository.save(court);
    }

    @Override
    public Court updateCourtStatus(Long id, Court.CourtStatus status) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with id: " + id));
        court.setStatus(status);
        return courtRepository.save(court);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Court> getCourtById(Long id) {
        return courtRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Court> getCourtByName(String courtName) {
        return courtRepository.findByCourtName(courtName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Court> getCourtsByStatus(Court.CourtStatus status) {
        return courtRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Court> getAvailableCourts(LocalDateTime startTime, LocalDateTime endTime) {
        return courtRepository.findAvailableCourts(startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Court> searchCourtsByName(String name) {
        return courtRepository.findByCourtNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getCourtAverageRating(Long courtId) {
        return courtRepository.getAverageRating(courtId);
    }

    @Override
    public void deleteCourt(Long id) {
        if (!courtRepository.existsById(id)) {
            throw new RuntimeException("Court not found with id: " + id);
        }
        courtRepository.deleteById(id);
    }
}