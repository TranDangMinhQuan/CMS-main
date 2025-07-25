package com.badminton.booking.dto;

import com.badminton.booking.enums.UserRole;

public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String username, String email, String fullName, UserRole role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}