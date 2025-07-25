package com.example.cmssystem.controller;

import com.example.cmssystem.dto.CourtDTO;
import com.example.cmssystem.service.CourtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Court Management", description = "APIs for managing badminton courts")
public class CourtController {
    
    private final CourtService courtService;
    
    @Operation(summary = "Get all active courts")
    @GetMapping
    public ResponseEntity<List<CourtDTO>> getAllCourts() {
        List<CourtDTO> courts = courtService.getAllActiveCourts();
        return ResponseEntity.ok(courts);
    }
    
    @Operation(summary = "Get courts with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<CourtDTO>> getCourtsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Page<CourtDTO> courts = courtService.getAllActiveCourts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(courts);
    }
    
    @Operation(summary = "Get court by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourtDTO> getCourtById(@PathVariable Long id) {
        return courtService.getCourtById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Search courts by keyword")
    @GetMapping("/search")
    public ResponseEntity<Page<CourtDTO>> searchCourts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<CourtDTO> courts = courtService.searchCourts(keyword, page, size);
        return ResponseEntity.ok(courts);
    }
    
    @Operation(summary = "Filter courts by criteria")
    @GetMapping("/filter")
    public ResponseEntity<Page<CourtDTO>> filterCourts(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<CourtDTO> courts = courtService.filterCourts(district, city, minPrice, maxPrice, page, size);
        return ResponseEntity.ok(courts);
    }
    
    @Operation(summary = "Get top rated courts")
    @GetMapping("/top-rated")
    public ResponseEntity<List<CourtDTO>> getTopRatedCourts(
            @RequestParam(defaultValue = "5") int limit) {
        
        List<CourtDTO> courts = courtService.getTopRatedCourts(limit);
        return ResponseEntity.ok(courts);
    }
    
    @Operation(summary = "Get courts by district")
    @GetMapping("/district/{district}")
    public ResponseEntity<List<CourtDTO>> getCourtsByDistrict(@PathVariable String district) {
        List<CourtDTO> courts = courtService.getCourtsByDistrict(district);
        return ResponseEntity.ok(courts);
    }
}