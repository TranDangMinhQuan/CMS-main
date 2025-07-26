package com.example.cmssystem.service.court;

import com.example.cmssystem.dto.court.CourtDTO;
import com.example.cmssystem.dto.court.CourtResponseDTO;

import java.util.List;

public interface CourtService {
    CourtDTO createCourt(CourtDTO dto);
    CourtDTO updateCourt(Long id, CourtDTO dto);
    CourtResponseDTO getCourtById(Long id);
    List<CourtResponseDTO> getAllActiveCourts();
    void softDeleteCourt(Long id);
}
