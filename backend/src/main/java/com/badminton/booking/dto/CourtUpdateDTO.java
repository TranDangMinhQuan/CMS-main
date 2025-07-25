package com.badminton.booking.dto;

import com.badminton.booking.entity.Court;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class CourtUpdateDTO {
    private String courtName;
    private String description;
    
    @Positive
    private BigDecimal pricePerHour;
    
    private Court.CourtStatus status;
    private String imageUrl;

    // Constructors
    public CourtUpdateDTO() {}

    // Getters and Setters
    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Court.CourtStatus getStatus() {
        return status;
    }

    public void setStatus(Court.CourtStatus status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}