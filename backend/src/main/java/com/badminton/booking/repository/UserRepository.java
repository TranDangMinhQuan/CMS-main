package com.badminton.booking.repository;

import com.badminton.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    List<User> findByRole(User.Role role);
    List<User> findByStatus(User.UserStatus status);
    List<User> findByRoleAndStatus(User.Role role, User.UserStatus status);
    
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name% OR u.username LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);
}