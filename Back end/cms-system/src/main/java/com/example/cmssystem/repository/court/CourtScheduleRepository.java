package com.example.cmssystem.repository.court;

import com.example.cmssystem.entity.court.CourtSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourtScheduleRepository extends JpaRepository<CourtSchedule, Long> {
    List<CourtSchedule> findByCourtIdAndDate(Long courtId, LocalDate date);
}

