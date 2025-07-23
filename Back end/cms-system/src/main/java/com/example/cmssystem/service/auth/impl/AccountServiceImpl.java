package com.example.cmssystem.service.auth.impl;


import com.example.cmssystem.dto.auth.AccountCreateDTO;
import com.example.cmssystem.dto.auth.AccountProfileDTO;
import com.example.cmssystem.dto.auth.AccountResponse;
import com.example.cmssystem.dto.auth.AccountUpdateDTO;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.enums.Role;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.repository.auth.AccountRepository;
import com.example.cmssystem.service.auth.AccountService;
import com.example.cmssystem.service.commons.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;


    @Override
    public void createByAdmin(AccountCreateDTO dto) {
        if (dto.getRole() == Role.MEMBER) {
            throw new IllegalArgumentException("Admin cannot create MEMBER accounts");
        }
        if (accountRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (accountRepo.existsByPhone(dto.getPhone())) {
            throw new IllegalArgumentException("Phone already in use");
        }
        if (accountRepo.existsByCCCD(dto.getCCCD())) {
            throw new IllegalArgumentException("CCCD already in use");
        }
        if (!dto.getCCCD().matches("\\d{12}")) {
            throw new IllegalArgumentException("CCCD must be exactly 12 digits");
        }
        Account account = new Account();
        account.setEmail(dto.getEmail());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setFullName(dto.getFullName());
        account.setGender(dto.getGender());
        account.setRole(dto.getRole());
        account.setDateOfBirth(dto.getDateOfBirth());
        account.setPhone(dto.getPhone());
        account.setAddress(dto.getAddress());
        account.setStatus(Status.ACTIVE);

        account.setCCCD(dto.getCCCD());
        emailService.sendLoginStaffAccount(dto);
        accountRepo.save(account);
    }

    @Override
    public void updateProfile(Account currentUser, AccountProfileDTO dto) {
        boolean phoneInUse = accountRepo.existsByPhone(dto.getPhone())
                && !dto.getPhone().equals(currentUser.getPhone());
        if (phoneInUse) {
            throw new IllegalArgumentException("Phone number already in use");
        }
        currentUser.setFullName(dto.getFullName());
        currentUser.setGender(dto.getGender());
        currentUser.setDateOfBirth(dto.getDateOfBirth());
        currentUser.setPhone(dto.getPhone());
        currentUser.setAddress(dto.getAddress());
        accountRepo.save(currentUser);
    }



    @Override
    public Object getProfile(Account currentUser) {
        if (currentUser.getRole() == Role.MEMBER) {
            AccountProfileDTO dto = modelMapper.map(currentUser, AccountProfileDTO.class);
            return dto;
        } else {
            AccountResponse response = modelMapper.map(currentUser, AccountResponse.class);
            response.setToken(null);
            return response;
        }
    }

    @Override
    public void updateByAdminOrStaff(Long id, AccountUpdateDTO dto) {
        Account acc = accountRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        boolean phoneInUse = accountRepo.existsByPhone(dto.getPhone())
                && !dto.getPhone().equals(acc.getPhone());
        if (phoneInUse) {
            throw new IllegalArgumentException("Phone number already in use");
        }
        acc.setFullName(dto.getFullName());
        acc.setGender(dto.getGender());
        acc.setDateOfBirth(dto.getDateOfBirth());
        acc.setPhone(dto.getPhone());
        acc.setAddress(dto.getAddress());


        accountRepo.save(acc);
    }

    @Override
    public List<AccountResponse> getAllByRole(Role role) {
        List<Account> accounts = accountRepo.findAllByRole(role);
        return accounts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void setSelfStatus(Long selfId, Status status) {
        Account self = accountRepo.findById(selfId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (self.getRole() != Role.MEMBER)
            throw new IllegalArgumentException("Only members can update their own status");
        self.setStatus(status);
        accountRepo.save(self);
    }

    @Override
    public void staffSetStatus(Long staffId, Long targetId, Status status) {
        if (staffId.equals(targetId))
            throw new IllegalArgumentException("Staff cannot modify their own account");
        Account target = accountRepo.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));
        if (target.getRole() != Role.MEMBER)
            throw new IllegalArgumentException("Staff can only modify MEMBER accounts");
        accountRepo.save(updateStatus(target, status));
    }

    @Override
    public void adminSetStatus(Long adminId, Long targetId, Status status) {
        Account target = accountRepo.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));
        if (target.getRole() == Role.MEMBER)
            throw new IllegalArgumentException("Admin cannot modify MEMBER accounts");
        accountRepo.save(updateStatus(target, status));
    }

    private Account updateStatus(Account account, Status status) {
        account.setStatus(status);
        return account;
    }

    public AccountResponse mapToResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }


}

