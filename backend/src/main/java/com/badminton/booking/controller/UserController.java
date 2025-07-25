package com.badminton.booking.controller;

import com.badminton.booking.dto.RegisterRequest;
import com.badminton.booking.entity.User;
import com.badminton.booking.enums.UserRole;
import com.badminton.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<User>> getActiveStaff() {
        return ResponseEntity.ok(userService.getActiveStaff());
    }

    @GetMapping("/members")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<User>> getActiveMembers() {
        return ResponseEntity.ok(userService.getActiveMembers());
    }

    @PostMapping("/staff")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<User> createStaff(@RequestBody RegisterRequest registerRequest) {
        User staff = userService.createStaff(registerRequest);
        return ResponseEntity.ok(staff);
    }

    @PutMapping("/{userId}/status")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<User> updateUserStatus(@PathVariable Long userId, @RequestParam boolean isActive) {
        User user = userService.updateUserStatus(userId, isActive);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<User> updateUserRole(@PathVariable Long userId, @RequestParam UserRole role) {
        User user = userService.updateUserRole(userId, role);
        return ResponseEntity.ok(user);
    }
}