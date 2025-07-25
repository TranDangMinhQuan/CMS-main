package com.example.cmssystem.entity.booking;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal discountAmount;

    private Double discountPercent;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private Boolean isActive;

    private Integer usageLimit;

    private Integer usedCount;
}
