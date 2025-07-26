package com.example.cmssystem.repository.court;

import com.example.cmssystem.entity.court.CourtPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtPackageRepository extends JpaRepository<CourtPackage, Long> {
    List<CourtPackage> findByCourtId(Long courtId);
}
