package com.example.cmssystem.service.court;

import com.example.cmssystem.dto.court.CourtScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public interface CourtScheduleService {
    CourtScheduleDTO create(CourtScheduleDTO dto);
    List<CourtScheduleDTO> getAll();
    List<CourtScheduleDTO> getByCourtAndDate(Long courtId, LocalDate date);
    CourtScheduleDTO update(Long id, CourtScheduleDTO dto);
    void delete(Long id);
}
