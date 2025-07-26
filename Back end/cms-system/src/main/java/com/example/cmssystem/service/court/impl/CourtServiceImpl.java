package com.example.cmssystem.service.court.impl;

import com.example.cmssystem.dto.court.CourtDTO;
import com.example.cmssystem.dto.court.CourtResponseDTO;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.court.CourtRepository;
import com.example.cmssystem.service.court.CourtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final ModelMapper mapper;

    @Override
    public CourtDTO createCourt(CourtDTO dto) {
        Court court = mapper.map(dto, Court.class);
        Court saved = courtRepository.save(court);
        return mapper.map(saved, CourtDTO.class);
    }

    @Override
    public CourtDTO updateCourt(Long id, CourtDTO dto) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));

        mapper.map(dto, court);
        Court updated = courtRepository.save(court);
        return mapper.map(updated, CourtDTO.class);
    }

    @Override
    public CourtResponseDTO getCourtById(Long id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));
        return mapper.map(court, CourtResponseDTO.class);
    }

    @Override
    public List<CourtResponseDTO> getAllActiveCourts() {
        return courtRepository.findByIsActiveTrue().stream()
                .map(court -> mapper.map(court, CourtResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteCourt(Long id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));
        court.setIsActive(false);
        court.setStatus(Status.INACTIVE);
        courtRepository.save(court);
    }
}
