package com.example.cmssystem.entity.court;

import com.example.cmssystem.entity.common.Auditable;
import com.example.cmssystem.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Court")
@Data
@EntityListeners(AuditingEntityListener.class) // 👈 Bắt buộc để @CreatedDate và @LastModifiedDate hoạt động
public class Court extends Auditable implements Serializable {

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
    private BigDecimal rating = BigDecimal.valueOf(0.0);

    @Column(name = "Total_Reviews")
    private Integer totalReviews = 0;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Facilities", columnDefinition = "TEXT")
    @Nationalized
    private String facilities;

    @Column(name = "Opening_Time")
    private String openingTime;

    @Column(name = "Closing_Time")
    private String closingTime;

    @Column(name = "Is_Active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status = Status.ACTIVE;

    @CreatedDate
    @Column(name = "Created_At", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;
}
