package com.example.cmssystem.api.court;

import com.example.cmssystem.dto.court.CourtPackageDTO;
import com.example.cmssystem.service.court.CourtPackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/court-packages")
@SecurityRequirement(name = "api")
public class CourtPackageAPI {

    @Autowired
    private CourtPackageService courtPackageService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public CourtPackageDTO create(@RequestBody CourtPackageDTO dto) {
        return courtPackageService.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GUEST', 'MEMBER', 'STAFF', 'OWNER')")
    public List<CourtPackageDTO> getAll() {
        return courtPackageService.getAll();
    }

    @GetMapping("/court/{courtId}")
    @PreAuthorize("hasAnyRole('GUEST', 'MEMBER', 'STAFF', 'OWNER')")
    public List<CourtPackageDTO> getByCourtId(@PathVariable Long courtId) {
        return courtPackageService.getByCourtId(courtId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public CourtPackageDTO update(@PathVariable Long id, @RequestBody CourtPackageDTO dto) {
        return courtPackageService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('STAFF')")
    public void delete(@PathVariable Long id) {
        courtPackageService.delete(id);
    }
}
