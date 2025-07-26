package com.example.cmssystem.service.booking;

import com.example.cmssystem.dto.booking.BookingPackageDTO;

import java.util.List;

public interface BookingPackageService {
    BookingPackageDTO create(BookingPackageDTO dto);
    BookingPackageDTO update(Long id, BookingPackageDTO dto);
    void delete(Long id);
    BookingPackageDTO getById(Long id);
    List<BookingPackageDTO> getAllActive();
}
