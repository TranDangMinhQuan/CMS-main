package com.example.cmssystem.entity.booking;

import com.example.cmssystem.entity.court.Court;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Booking_Code", nullable = false, unique = true)
    private String bookingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Court_Id", nullable = false)
    private Court court;

    @Column(name = "Booking_Date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "Start_Time", nullable = false)
    private LocalTime startTime;

    @Column(name = "End_Time", nullable = false)
    private LocalTime endTime;

    @Column(name = "Total_Hours")
    private BigDecimal totalHours;

    @Column(name = "Price_Per_Hour")
    private BigDecimal pricePerHour;

    @Column(name = "Total_Amount")
    private BigDecimal totalAmount;

    @Column(name = "Discount_Amount")
    private BigDecimal discountAmount;

    @Column(name = "Final_Amount")
    private BigDecimal finalAmount;

    @Column(name = "Status")
    private String status;

    @Column(name = "Customer_Name")
    private String customerName;

    @Column(name = "Customer_Phone")
    private String customerPhone;

    @Column(name = "Customer_Email")
    private String customerEmail;

    @Column(name = "Notes")
    private String notes;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Confirmed_At")
    private LocalDateTime confirmedAt;

    @Column(name = "Cancelled_At")
    private LocalDateTime cancelledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private BookingPackage bookingPackage;


}
