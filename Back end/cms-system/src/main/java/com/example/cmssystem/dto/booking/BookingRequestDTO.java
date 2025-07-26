package com.example.cmssystem.dto.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private Long courtId;
    private Long packageId;

    private String fullName;
    private String phone;
    private String email;
    private String cccd;

    private String couponCode;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long couponId;
    private BigDecimal discountAmountApplied;

}
