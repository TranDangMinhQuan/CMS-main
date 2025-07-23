package com.example.cmssystem.controller.auth;

import com.example.cmssystem.dto.auth.*;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationApi {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest account) {
        Account newAccount = authenticationService.register(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        AccountResponse account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/forgot-password-request")
    public ResponseEntity forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok("Check your email to reset your password.");
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully.");
    }
}