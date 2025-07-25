package com.badminton.booking.controller;

import com.badminton.booking.entity.Court;
import com.badminton.booking.enums.CourtStatus;
import com.badminton.booking.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/courts")
@CrossOrigin(origins = "*")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @GetMapping
    public ResponseEntity<List<Court>> getAllCourts() {
        return ResponseEntity.ok(courtService.getAllCourts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getCourtById(id));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Court>> getAvailableCourts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        
        if (startTime != null && endTime != null) {
            return ResponseEntity.ok(courtService.getAvailableCourts(startTime, endTime));
        } else {
            return ResponseEntity.ok(courtService.getAvailableCourts());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Court> createCourt(@RequestBody Court court) {
        return ResponseEntity.ok(courtService.createCourt(court));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Court> updateCourt(@PathVariable Long id, @RequestBody Court court) {
        return ResponseEntity.ok(courtService.updateCourt(id, court));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<Court> updateCourtStatus(@PathVariable Long id, @RequestParam CourtStatus status) {
        return ResponseEntity.ok(courtService.updateCourtStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return ResponseEntity.ok().build();
    }
}