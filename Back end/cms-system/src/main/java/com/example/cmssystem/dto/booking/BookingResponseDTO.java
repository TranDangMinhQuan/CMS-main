package com.example.cmssystem.dto.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;

    private String courtName;
    private String packageName;
    private Integer durationMinutes;
    private BigDecimal price;

    private String fullName;
    private String phone;
    private String email;
    private String cccd;

    private String couponCode;
    private boolean checkedIn;
    private LocalDateTime checkInTime;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;

    private String bookedByUsername;
}
