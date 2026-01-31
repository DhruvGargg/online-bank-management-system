package org.example.onlinebankmanagementbackend.admin.service;

import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;

import java.util.List;

public interface AdminService
{
    List<AccountResponseDTO> getAllAccounts();
    void freezeAccount(Long accountId);
    void activateAccount(Long accountId);
}
