package com.example.cmssystem.dto.auth;

import com.example.cmssystem.enums.Gender;
import com.example.cmssystem.enums.Role;
import com.example.cmssystem.enums.Status;
import lombok.Data;

import java.sql.Date;

@Data
public class AccountResponse {
    private Long id;
    private String email;
    private Role role;
    private String fullName;
    private Gender gender;
    private Date dateOfBirth;
    private String phone;
    private String address;
    private Date createAt;
    private Status status;
    private String token;
}
