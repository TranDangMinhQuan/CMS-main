package com.badminton.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerRacket = new BigDecimal("30000"); // 30k per racket

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 500)
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public RacketRental() {}

    public RacketRental(Booking booking, Integer quantity) {
        this.booking = booking;
        this.quantity = quantity;
        this.totalAmount = pricePerRacket.multiply(new BigDecimal(quantity));
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
        if (quantity != null) {
            this.totalAmount = pricePerRacket.multiply(new BigDecimal(quantity));
        }
    }

    public BigDecimal getPricePerRacket() {
        return pricePerRacket;
    }

    public void setPricePerRacket(BigDecimal pricePerRacket) {
        this.pricePerRacket = pricePerRacket;
        if (quantity != null) {
            this.totalAmount = pricePerRacket.multiply(new BigDecimal(quantity));
        }
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}