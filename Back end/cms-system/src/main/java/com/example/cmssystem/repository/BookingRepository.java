package com.example.cmssystem.repository;

import com.example.cmssystem.entity.booking.Booking;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByBookingCode(String bookingCode);
    
    List<Booking> findByCustomerOrderByCreatedAtDesc(Account customer);
    
    Page<Booking> findByCustomerOrderByCreatedAtDesc(Account customer, Pageable pageable);
    
    List<Booking> findByCourtAndBookingDateOrderByStartTime(Court court, LocalDate bookingDate);
    
    @Query("SELECT b FROM Booking b WHERE b.court = :court AND b.bookingDate = :date AND " +
           "b.status IN (:statuses) AND " +
           "((b.startTime <= :endTime AND b.endTime > :startTime))")
    List<Booking> findConflictingBookings(@Param("court") Court court,
                                        @Param("date") LocalDate date,
                                        @Param("startTime") LocalTime startTime,
                                        @Param("endTime") LocalTime endTime,
                                        @Param("statuses") List<BookingStatus> statuses);
    
    @Query("SELECT b FROM Booking b WHERE b.status = :status AND b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findBookingsByStatusAndDateRange(@Param("status") BookingStatus status,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND b.status = :status")
    List<Booking> findByCustomerAndStatus(@Param("customer") Account customer,
                                         @Param("status") BookingStatus status);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.court = :court AND b.bookingDate = :date AND b.status IN (:statuses)")
    Long countBookingsByCourtAndDate(@Param("court") Court court,
                                   @Param("date") LocalDate date,
                                   @Param("statuses") List<BookingStatus> statuses);
    
    @Query("SELECT b FROM Booking b WHERE b.bookingDate >= :fromDate AND b.status IN (:statuses)")
    Page<Booking> findUpcomingBookings(@Param("fromDate") LocalDate fromDate,
                                      @Param("statuses") List<BookingStatus> statuses,
                                      Pageable pageable);
    
    List<Booking> findByStatusAndCreatedAtBefore(BookingStatus status, LocalDateTime dateTime);
}