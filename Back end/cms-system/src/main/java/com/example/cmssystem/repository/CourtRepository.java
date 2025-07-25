package com.example.cmssystem.repository;

import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    
    List<Court> findByStatusAndIsActiveTrue(Status status);
    
    Page<Court> findByStatusAndIsActiveTrue(Status status, Pageable pageable);
    
    @Query("SELECT c FROM Court c WHERE c.status = :status AND c.isActive = true AND " +
           "(:district IS NULL OR c.district LIKE %:district%) AND " +
           "(:city IS NULL OR c.city LIKE %:city%) AND " +
           "(:minPrice IS NULL OR c.pricePerHour >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.pricePerHour <= :maxPrice)")
    Page<Court> findCourtsWithFilters(@Param("status") Status status,
                                     @Param("district") String district,
                                     @Param("city") String city,
                                     @Param("minPrice") BigDecimal minPrice,
                                     @Param("maxPrice") BigDecimal maxPrice,
                                     Pageable pageable);
    
    @Query("SELECT c FROM Court c WHERE c.status = :status AND c.isActive = true AND " +
           "(c.name LIKE %:keyword% OR c.address LIKE %:keyword% OR c.district LIKE %:keyword%)")
    Page<Court> searchCourts(@Param("status") Status status,
                            @Param("keyword") String keyword,
                            Pageable pageable);
    
    Optional<Court> findByIdAndStatusAndIsActiveTrue(Long id, Status status);
    
    List<Court> findByDistrictAndStatusAndIsActiveTrue(String district, Status status);
    
    @Query("SELECT c FROM Court c WHERE c.status = :status AND c.isActive = true ORDER BY c.rating DESC")
    Page<Court> findTopRatedCourts(@Param("status") Status status, Pageable pageable);
}