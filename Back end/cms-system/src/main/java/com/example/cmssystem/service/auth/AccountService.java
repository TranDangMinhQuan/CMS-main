package com.example.cmssystem.service.auth;



import com.example.cmssystem.dto.auth.AccountCreateDTO;
import com.example.cmssystem.dto.auth.AccountProfileDTO;
import com.example.cmssystem.dto.auth.AccountResponse;
import com.example.cmssystem.dto.auth.AccountUpdateDTO;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.enums.Role;
import com.example.cmssystem.enums.Status;

import java.util.List;

public interface AccountService {
    void createByAdmin(AccountCreateDTO dto);
    void updateProfile(Account currentUser, AccountProfileDTO dto);
    Object getProfile(Account currentUser);
    void updateByAdminOrStaff(Long id, AccountUpdateDTO dto);
    List<AccountResponse> getAllByRole(Role role);
    void setSelfStatus(Long selfId, Status status);
    void staffSetStatus(Long staffId, Long targetId, Status status);
    void adminSetStatus(Long adminId, Long targetId, Status status);

}
