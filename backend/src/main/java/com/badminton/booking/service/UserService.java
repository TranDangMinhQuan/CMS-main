package com.badminton.booking.service;

import com.badminton.booking.entity.User;
import com.badminton.booking.dto.UserRegistrationDTO;
import com.badminton.booking.dto.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserRegistrationDTO userRegistrationDTO);
    User updateUser(Long id, UserUpdateDTO userUpdateDTO);
    User updateUserRole(Long id, User.Role role);
    User updateUserStatus(Long id, User.UserStatus status);
    
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByRole(User.Role role);
    List<User> getUsersByStatus(User.UserStatus status);
    List<User> searchUsersByName(String name);
    
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}