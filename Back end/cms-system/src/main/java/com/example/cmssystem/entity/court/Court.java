package com.example.cmssystem.entity.court;

import com.example.cmssystem.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Court")
@Data
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @Nationalized
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    @Nationalized
    private String description;

    @Column(name = "Address", nullable = false)
    @Nationalized
    private String address;

    @Column(name = "District")
    @Nationalized
    private String district;

    @Column(name = "City")
    @Nationalized
    private String city;

    @Column(name = "Price_Per_Hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "Image_Url")
    private String imageUrl;

    @Column(name = "Rating", precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "Total_Reviews")
    private Integer totalReviews = 0;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Facilities", columnDefinition = "TEXT")
    @Nationalized
    private String facilities; // JSON string hoặc comma separated

    @Column(name = "Opening_Time")
    private String openingTime; // Format: "06:00"

    @Column(name = "Closing_Time") 
    private String closingTime; // Format: "23:00"

    @Column(name = "Is_Active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
        if (this.rating == null) {
            this.rating = BigDecimal.valueOf(0.0);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
