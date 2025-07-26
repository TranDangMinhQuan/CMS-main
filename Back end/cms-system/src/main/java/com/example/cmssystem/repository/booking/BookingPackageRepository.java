package com.example.cmssystem.repository.booking;

import com.example.cmssystem.entity.booking.BookingPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingPackageRepository extends JpaRepository<BookingPackage, Long> {
    Optional<BookingPackage> findByName(String name);
}
