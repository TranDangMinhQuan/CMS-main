package com.badminton.booking.controller;

import com.badminton.booking.dto.CourtCreateDTO;
import com.badminton.booking.dto.CourtUpdateDTO;
import com.badminton.booking.entity.Court;
import com.badminton.booking.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourtController {

    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    // Public endpoints (Guest accessible)
    @GetMapping("/public")
    public ResponseEntity<List<Court>> getAllCourtsPublic() {
        List<Court> courts = courtService.getCourtsByStatus(Court.CourtStatus.AVAILABLE);
        return ResponseEntity.ok(courts);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getCourtByIdPublic(@PathVariable Long id) {
        return courtService.getCourtById(id)
            .map(court -> {
                Double averageRating = courtService.getCourtAverageRating(id);
                return ResponseEntity.ok(Map.of(
                    "court", court,
                    "averageRating", averageRating != null ? averageRating : 0.0
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/public/available")
    public ResponseEntity<List<Court>> getAvailableCourtsPublic(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<Court> availableCourts = courtService.getAvailableCourts(startTime, endTime);
        return ResponseEntity.ok(availableCourts);
    }

    @GetMapping("/public/search")
    public ResponseEntity<List<Court>> searchCourtsPublic(@RequestParam String name) {
        List<Court> courts = courtService.searchCourtsByName(name);
        return ResponseEntity.ok(courts);
    }

    // Protected endpoints (Authenticated users)
    @GetMapping
    @PreAuthorize("hasRole('MEMBER') or hasRole('STAFF') or hasRole('OWNER')")
    public ResponseEntity<List<Court>> getAllCourts() {
        List<Court> courts = courtService.getAllCourts();
        return ResponseEntity.ok(courts);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('STAFF') or hasRole('OWNER')")
    public ResponseEntity<?> getCourtById(@PathVariable Long id) {
        return courtService.getCourtById(id)
            .map(court -> {
                Double averageRating = courtService.getCourtAverageRating(id);
                return ResponseEntity.ok(Map.of(
                    "court", court,
                    "averageRating", averageRating != null ? averageRating : 0.0
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Admin endpoints (Staff and Owner)
    @PostMapping
    @PreAuthorize("hasRole('STAFF') or hasRole('OWNER')")
    public ResponseEntity<?> createCourt(@Valid @RequestBody CourtCreateDTO courtCreateDTO) {
        try {
            Court court = courtService.createCourt(courtCreateDTO);
            return ResponseEntity.ok(Map.of(
                "message", "Court created successfully!",
                "court", court
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('OWNER')")
    public ResponseEntity<?> updateCourt(@PathVariable Long id, @Valid @RequestBody CourtUpdateDTO courtUpdateDTO) {
        try {
            Court court = courtService.updateCourt(id, courtUpdateDTO);
            return ResponseEntity.ok(Map.of(
                "message", "Court updated successfully!",
                "court", court
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> updateCourtStatus(@PathVariable Long id, @RequestBody Map<String, String> statusRequest) {
        try {
            Court.CourtStatus status = Court.CourtStatus.valueOf(statusRequest.get("status"));
            Court court = courtService.updateCourtStatus(id, status);
            return ResponseEntity.ok(Map.of(
                "message", "Court status updated successfully!",
                "court", court
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> deleteCourt(@PathVariable Long id) {
        try {
            courtService.deleteCourt(id);
            return ResponseEntity.ok(Map.of("message", "Court deleted successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}