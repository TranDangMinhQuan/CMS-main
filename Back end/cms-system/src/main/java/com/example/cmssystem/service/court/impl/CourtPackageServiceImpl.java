package com.example.cmssystem.service.court.impl;

import com.example.cmssystem.dto.court.CourtPackageDTO;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.entity.court.CourtPackage;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.court.CourtPackageRepository;
import com.example.cmssystem.repository.court.CourtRepository;
import com.example.cmssystem.service.court.CourtPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtPackageServiceImpl implements CourtPackageService {

    @Autowired
    private CourtPackageRepository packageRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Override
    public CourtPackageDTO create(CourtPackageDTO dto) {
        Court court = courtRepository.findById(dto.getCourtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));

        CourtPackage courtPackage = new CourtPackage();
        courtPackage.setCourt(court);
        courtPackage.setName(dto.getName());
        courtPackage.setDurationMinutes(dto.getDurationMinutes());
        courtPackage.setPrice(dto.getPrice());
        courtPackage.setDescription(dto.getDescription());

        CourtPackage saved = packageRepository.save(courtPackage);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<CourtPackageDTO> getAll() {
        return packageRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourtPackageDTO> getByCourtId(Long courtId) {
        return packageRepository.findByCourtId(courtId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourtPackageDTO update(Long id, CourtPackageDTO dto) {
        CourtPackage cp = packageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court Package not found"));

        cp.setName(dto.getName());
        cp.setDurationMinutes(dto.getDurationMinutes());
        cp.setPrice(dto.getPrice());
        cp.setDescription(dto.getDescription());

        CourtPackage updated = packageRepository.save(cp);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!packageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Court Package not found");
        }
        packageRepository.deleteById(id);
    }

    private CourtPackageDTO toDTO(CourtPackage cp) {
        CourtPackageDTO dto = new CourtPackageDTO();
        dto.setId(cp.getId());
        dto.setCourtId(cp.getCourt().getId());
        dto.setName(cp.getName());
        dto.setDurationMinutes(cp.getDurationMinutes());
        dto.setPrice(cp.getPrice());
        dto.setDescription(cp.getDescription());
        return dto;
    }
}
