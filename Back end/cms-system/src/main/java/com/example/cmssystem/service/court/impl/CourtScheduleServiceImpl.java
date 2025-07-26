package com.example.cmssystem.service.court.impl;

import com.example.cmssystem.dto.court.CourtScheduleDTO;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.entity.court.CourtSchedule;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.court.CourtRepository;
import com.example.cmssystem.repository.court.CourtScheduleRepository;
import com.example.cmssystem.service.court.CourtScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtScheduleServiceImpl implements CourtScheduleService {

    @Autowired
    private CourtScheduleRepository scheduleRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Override
    public CourtScheduleDTO create(CourtScheduleDTO dto) {
        Court court = courtRepository.findById(dto.getCourtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));

        CourtSchedule schedule = new CourtSchedule();
        schedule.setCourt(court);
        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setStatus(dto.getStatus());

        schedule = scheduleRepository.save(schedule);
        dto.setId(schedule.getId());
        return dto;
    }

    @Override
    public List<CourtScheduleDTO> getAll() {
        return scheduleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourtScheduleDTO> getByCourtAndDate(Long courtId, java.time.LocalDate date) {
        return scheduleRepository.findByCourtIdAndDate(courtId, date).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourtScheduleDTO update(Long id, CourtScheduleDTO dto) {
        CourtSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court schedule not found"));

        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setStatus(dto.getStatus());

        schedule = scheduleRepository.save(schedule);
        return toDTO(schedule);
    }

    @Override
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    private CourtScheduleDTO toDTO(CourtSchedule s) {
        CourtScheduleDTO dto = new CourtScheduleDTO();
        dto.setId(s.getId());
        dto.setCourtId(s.getCourt().getId());
        dto.setDate(s.getDate());
        dto.setStartTime(s.getStartTime());
        dto.setEndTime(s.getEndTime());
        dto.setStatus(s.getStatus());
        return dto;
    }
}
