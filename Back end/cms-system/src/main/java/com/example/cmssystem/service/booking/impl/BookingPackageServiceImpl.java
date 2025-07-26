package com.example.cmssystem.service.booking.impl;

import com.example.cmssystem.dto.booking.BookingPackageDTO;
import com.example.cmssystem.entity.booking.BookingPackage;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.booking.BookingPackageRepository;
import com.example.cmssystem.service.booking.BookingPackageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingPackageServiceImpl implements BookingPackageService {

    @Autowired
    private BookingPackageRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public BookingPackageDTO create(BookingPackageDTO dto) {
        BookingPackage entity = mapper.map(dto, BookingPackage.class);
        return mapper.map(repository.save(entity), BookingPackageDTO.class);
    }

    @Override
    public BookingPackageDTO update(Long id, BookingPackageDTO dto) {
        BookingPackage existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Package not found"));
        existing.setName(dto.getName());
        existing.setDurationMinutes(dto.getDurationMinutes());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setIsActive(dto.getIsActive());

        return mapper.map(repository.save(existing), BookingPackageDTO.class);
    }

    @Override
    public void delete(Long id) {
        BookingPackage existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Package not found"));
        repository.delete(existing);
    }

    @Override
    public BookingPackageDTO getById(Long id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, BookingPackageDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Booking Package not found"));
    }

    @Override
    public List<BookingPackageDTO> getAllActive() {
        return repository.findAll().stream()
                .filter(BookingPackage::getIsActive)
                .map(entity -> mapper.map(entity, BookingPackageDTO.class))
                .collect(Collectors.toList());
    }
}
