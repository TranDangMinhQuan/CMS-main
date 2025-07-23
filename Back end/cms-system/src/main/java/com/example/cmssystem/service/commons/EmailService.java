package com.example.cmssystem.service.commons;


import com.example.cmssystem.dto.auth.AccountCreateDTO;
import com.example.cmssystem.dto.commons.EmailDetailForForgotPassword;
import com.example.cmssystem.dto.commons.EmailDetailForRegister;
import com.example.cmssystem.entity.auth.Account;

import java.time.LocalDate;

public interface EmailService {
    void sendRegisterSuccessEmail(EmailDetailForRegister emailDetail);
    void sendResetPasswordEmail(EmailDetailForForgotPassword emailDetailForForgotPassword);
    void sendLoginStaffAccount(AccountCreateDTO emailDetail);
    void sendReminderEmail(Account donor, LocalDate nextDate);
}
