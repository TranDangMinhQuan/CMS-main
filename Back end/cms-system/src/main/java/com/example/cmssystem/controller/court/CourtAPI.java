package com.example.cmssystem.api.court;

import com.example.cmssystem.dto.court.CourtDTO;
import com.example.cmssystem.dto.court.CourtResponseDTO;
import com.example.cmssystem.service.court.CourtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
@SecurityRequirement(name = "api")
public class CourtAPI {

    private final CourtService courtService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public ResponseEntity<CourtDTO> create(@RequestBody CourtDTO dto) {
        return ResponseEntity.ok(courtService.createCourt(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public ResponseEntity<CourtDTO> update(@PathVariable Long id, @RequestBody CourtDTO dto) {
        return ResponseEntity.ok(courtService.updateCourt(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GUEST', 'MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<CourtResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getCourtById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GUEST', 'MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<List<CourtResponseDTO>> getAllActive() {
        return ResponseEntity.ok(courtService.getAllActiveCourts());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courtService.softDeleteCourt(id);
        return ResponseEntity.noContent().build();
    }
}
