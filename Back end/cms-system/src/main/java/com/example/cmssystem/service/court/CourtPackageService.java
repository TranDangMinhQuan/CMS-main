package com.example.cmssystem.service.court;

import com.example.cmssystem.dto.court.CourtPackageDTO;

import java.util.List;

public interface CourtPackageService {
    CourtPackageDTO create(CourtPackageDTO dto);
    List<CourtPackageDTO> getAll();
    List<CourtPackageDTO> getByCourtId(Long courtId);
    CourtPackageDTO update(Long id, CourtPackageDTO dto);
    void delete(Long id);
}
