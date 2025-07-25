package com.badminton.booking.service;

import com.badminton.booking.dto.AuthResponse;
import com.badminton.booking.dto.LoginRequest;
import com.badminton.booking.dto.RegisterRequest;
import com.badminton.booking.entity.User;
import com.badminton.booking.enums.UserRole;

import java.util.List;

public interface UserService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<User> getUsersByRole(UserRole role);
    User createStaff(RegisterRequest registerRequest);
    User updateUserStatus(Long userId, boolean isActive);
    User updateUserRole(Long userId, UserRole role);
    List<User> getActiveStaff();
    List<User> getActiveMembers();
}