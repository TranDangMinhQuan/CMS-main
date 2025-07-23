package com.example.cmssystem.repository.auth;

import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    Account findAccountByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCCCD(String cccd);
    Optional<Account> findByEmail(String email);
    boolean existsByRole(Role role);
}
