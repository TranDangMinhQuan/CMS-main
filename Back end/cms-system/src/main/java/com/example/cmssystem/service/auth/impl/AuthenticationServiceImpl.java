package com.example.cmssystem.service.auth.impl;


import com.example.cmssystem.dto.auth.AccountResponse;
import com.example.cmssystem.dto.auth.ForgotPasswordRequest;
import com.example.cmssystem.dto.auth.LoginRequest;
import com.example.cmssystem.dto.auth.RegisterRequest;
import com.example.cmssystem.dto.commons.EmailDetailForForgotPassword;
import com.example.cmssystem.dto.commons.EmailDetailForRegister;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.enums.Role;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.exception.exceptions.AuthenticationException;
import com.example.cmssystem.repository.auth.AuthenticationRepository;
import com.example.cmssystem.service.auth.AuthenticationService;
import com.example.cmssystem.service.auth.TokenService;
import com.example.cmssystem.service.commons.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public Account register(RegisterRequest dto) {
        if (authenticationRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (authenticationRepository.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Phone number already in use");
        }
        if (authenticationRepository.existsByCCCD(dto.getCccd())) {
            throw new IllegalArgumentException("CCCD already in use");
        }
        if (!dto.getCccd().matches("\\d{12}")) {
            throw new IllegalArgumentException("CCCD must be exactly 12 digits");
        }
        Account account = new Account();
        account.setEmail(dto.getEmail());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setFullName(dto.getFullName());
        account.setGender(dto.getGender());
        account.setDateOfBirth(dto.getDateOfBirth());
        account.setPhone(dto.getPhone());
        account.setAddress(dto.getAddress());
        account.setCCCD(dto.getCccd());
        account.setRole(Role.MEMBER);
        account.setStatus(Status.ACTIVE);
        EmailDetailForRegister emailDetailForRegister = new EmailDetailForRegister();
        emailDetailForRegister.setToEmail(dto.getEmail());
        emailDetailForRegister.setSubject("Welcome to BDS System");
        emailService.sendRegisterSuccessEmail(emailDetailForRegister);
        return authenticationRepository.save(account);
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));
        } catch (Exception e) {
            System.out.println("Error");
            throw new AuthenticationException("Invalid username or password");
        }
        Account account = authenticationRepository.findAccountByEmail(loginRequest.getEmail());
        AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
        String token = tokenService.generateToken(account);
        accountResponse.setToken(token);
        return accountResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) authenticationRepository.findAccountByEmail(email);
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        Account account = authenticationRepository.findAccountByEmail(request.getEmail());
        if (account == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại");
        } else {
            EmailDetailForForgotPassword emailDetailForForgotPassword = new EmailDetailForForgotPassword();
            emailDetailForForgotPassword.setAccount(account);
            emailDetailForForgotPassword.setSubject("Reset Password");
            emailDetailForForgotPassword.setLink("http://localhost:5173/reset-password?token=" + tokenService.generateToken(account));
            emailService.sendResetPasswordEmail(emailDetailForForgotPassword);
        }
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Account account = tokenService.extractAccount(token);
        if (account == null) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        account.setPassword(passwordEncoder.encode(newPassword));
        authenticationRepository.save(account);
    }
}