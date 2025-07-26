package com.example.cmssystem.repository.court;

import com.example.cmssystem.entity.court.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    List<Court> findByIsActiveTrue();
}
