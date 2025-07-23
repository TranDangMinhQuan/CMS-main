package com.example.cmssystem.service.auth;

import com.example.cmssystem.dto.auth.AccountResponse;
import com.example.cmssystem.dto.auth.ForgotPasswordRequest;
import com.example.cmssystem.dto.auth.LoginRequest;
import com.example.cmssystem.dto.auth.RegisterRequest;
import com.example.cmssystem.entity.auth.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService {
    Account register(RegisterRequest dto);
    AccountResponse login(LoginRequest loginRequest);
    UserDetails loadUserByUsername(String email);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(String token, String newPassword);
}
