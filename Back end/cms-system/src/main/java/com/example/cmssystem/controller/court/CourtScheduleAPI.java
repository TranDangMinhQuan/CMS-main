package com.example.cmssystem.api.court;

import com.example.cmssystem.dto.court.CourtScheduleDTO;
import com.example.cmssystem.service.court.CourtScheduleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/court-schedules")
@SecurityRequirement(name = "api")
public class CourtScheduleAPI {

    @Autowired
    private CourtScheduleService courtScheduleService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public CourtScheduleDTO create(@RequestBody CourtScheduleDTO dto) {
        return courtScheduleService.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER', 'STAFF', 'MEMBER')")
    public List<CourtScheduleDTO> getAll() {
        return courtScheduleService.getAll();
    }

    @GetMapping("/court/{courtId}/date")
    @PreAuthorize("hasAnyRole('OWNER', 'STAFF', 'MEMBER')")
    public List<CourtScheduleDTO> getByCourtAndDate(
            @PathVariable Long courtId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return courtScheduleService.getByCourtAndDate(courtId, date);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public CourtScheduleDTO update(@PathVariable Long id, @RequestBody CourtScheduleDTO dto) {
        return courtScheduleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public void delete(@PathVariable Long id) {
        courtScheduleService.delete(id);
    }
}
