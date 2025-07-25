package com.example.cmssystem.entity.booking;

import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_Id", nullable = false)
    private Account customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Court_Id", nullable = false)
    private Court court;

    @Column(name = "Booking_Date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "Start_Time", nullable = false)
    private LocalTime startTime;

    @Column(name = "End_Time", nullable = false)
    private LocalTime endTime;

    @Column(name = "Total_Hours", nullable = false)
    private BigDecimal totalHours;

    @Column(name = "Price_Per_Hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "Total_Amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Discount_Amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "Final_Amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private BookingStatus status;

    @Column(name = "Customer_Name", nullable = false)
    @Nationalized
    private String customerName;

    @Column(name = "Customer_Phone", nullable = false)
    private String customerPhone;

    @Column(name = "Customer_Email")
    private String customerEmail;

    @Column(name = "Notes", columnDefinition = "TEXT")
    @Nationalized
    private String notes;

    @Column(name = "Booking_Code", unique = true, nullable = false)
    private String bookingCode;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Confirmed_At")
    private LocalDateTime confirmedAt;

    @Column(name = "Cancelled_At")
    private LocalDateTime cancelledAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
        // Generate booking code
        this.bookingCode = "BK" + System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
