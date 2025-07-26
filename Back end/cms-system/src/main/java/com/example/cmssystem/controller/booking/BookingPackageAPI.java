package com.example.cmssystem.controller.booking;

import com.example.cmssystem.dto.booking.BookingPackageDTO;
import com.example.cmssystem.service.booking.BookingPackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-packages")
@RequiredArgsConstructor
@SecurityRequirement(name = "api")
public class BookingPackageAPI {

    private final BookingPackageService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<BookingPackageDTO> create(@RequestBody BookingPackageDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<BookingPackageDTO> update(@PathVariable Long id, @RequestBody BookingPackageDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingPackageDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookingPackageDTO>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }
}
