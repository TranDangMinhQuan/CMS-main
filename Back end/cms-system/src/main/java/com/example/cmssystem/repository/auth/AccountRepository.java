package com.example.cmssystem.repository.auth;

import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCCCD(String cccd);
    Optional<Account> findByEmail(String email);
}