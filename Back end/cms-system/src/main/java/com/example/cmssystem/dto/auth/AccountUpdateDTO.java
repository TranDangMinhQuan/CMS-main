package com.example.cmssystem.dto.auth;

import com.example.cmssystem.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.sql.Date;

@Data
public class AccountUpdateDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(0[0-9]{9})$", message = "Phone number must start with 0 and have 10 digits")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    private Long bloodTypeId;


}

