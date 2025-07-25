package com.badminton.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "racket_rentals")
public class RacketRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Price per racket is required")
    @Column(name = "price_per_racket", precision = 10, scale = 2)
    private BigDecimal pricePerRacket = new BigDecimal("30000"); // 30k per racket

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public RacketRental() {}

    public RacketRental(Booking booking, Integer quantity) {
        this.booking = booking;
        this.quantity = quantity;
        this.totalAmount = this.pricePerRacket.multiply(new BigDecimal(quantity));
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        if (this.pricePerRacket != null) {
            this.totalAmount = this.pricePerRacket.multiply(new BigDecimal(quantity));
        }
    }

    public BigDecimal getPricePerRacket() {
        return pricePerRacket;
    }

    public void setPricePerRacket(BigDecimal pricePerRacket) {
        this.pricePerRacket = pricePerRacket;
        if (this.quantity != null) {
            this.totalAmount = pricePerRacket.multiply(new BigDecimal(this.quantity));
        }
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}