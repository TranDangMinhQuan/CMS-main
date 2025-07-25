package com.example.cmssystem.service;

import com.example.cmssystem.dto.CourtDTO;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.repository.CourtRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtService {
    
    private final CourtRepository courtRepository;
    private final ModelMapper modelMapper;
    
    public List<CourtDTO> getAllActiveCourts() {
        List<Court> courts = courtRepository.findByStatusAndIsActiveTrue(Status.ACTIVE);
        return courts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Page<CourtDTO> getAllActiveCourts(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Court> courts = courtRepository.findByStatusAndIsActiveTrue(Status.ACTIVE, pageable);
        return courts.map(this::convertToDTO);
    }
    
    public Optional<CourtDTO> getCourtById(Long id) {
        Optional<Court> court = courtRepository.findByIdAndStatusAndIsActiveTrue(id, Status.ACTIVE);
        return court.map(this::convertToDTO);
    }
    
    public Page<CourtDTO> searchCourts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "rating"));
        Page<Court> courts = courtRepository.searchCourts(Status.ACTIVE, keyword, pageable);
        return courts.map(this::convertToDTO);
    }
    
    public Page<CourtDTO> filterCourts(String district, String city, 
                                      BigDecimal minPrice, BigDecimal maxPrice,
                                      int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "rating"));
        Page<Court> courts = courtRepository.findCourtsWithFilters(
            Status.ACTIVE, district, city, minPrice, maxPrice, pageable);
        return courts.map(this::convertToDTO);
    }
    
    public List<CourtDTO> getTopRatedCourts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<Court> courts = courtRepository.findTopRatedCourts(Status.ACTIVE, pageable);
        return courts.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CourtDTO> getCourtsByDistrict(String district) {
        List<Court> courts = courtRepository.findByDistrictAndStatusAndIsActiveTrue(district, Status.ACTIVE);
        return courts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private CourtDTO convertToDTO(Court court) {
        CourtDTO dto = modelMapper.map(court, CourtDTO.class);
        
        // Convert facilities string to list
        if (court.getFacilities() != null && !court.getFacilities().isEmpty()) {
            dto.setFacilities(Arrays.asList(court.getFacilities().split(",")));
        }
        
        // Set status as string
        dto.setStatus(court.getStatus().name());
        
        return dto;
    }
}