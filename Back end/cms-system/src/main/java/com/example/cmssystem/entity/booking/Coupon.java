package com.example.cmssystem.entity.booking;

import com.example.cmssystem.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Coupon")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Code", nullable = false, unique = true)
    private String code;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Discount_Type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // PERCENTAGE, FIXED_AMOUNT

    @Column(name = "Discount_Value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "Min_Order_Amount", precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(name = "Max_Discount_Amount", precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "Usage_Limit")
    private Integer usageLimit;

    @Column(name = "Used_Count")
    private Integer usedCount = 0;

    @Column(name = "Start_Date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "End_Date", nullable = false)
    private LocalDateTime endDate;

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
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT
    }
}
