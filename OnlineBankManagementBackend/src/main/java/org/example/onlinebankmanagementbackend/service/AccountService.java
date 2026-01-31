package org.example.onlinebankmanagementbackend.service;

import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.example.onlinebankmanagementbackend.dto.account.CreateAccountRequestDTO;

import java.util.List;

public interface AccountService
{
    AccountResponseDTO createAccount(CreateAccountRequestDTO createAccountRequestDTO);
    List<AccountResponseDTO> getMyAccounts();
    AccountResponseDTO getAccountByAccountId(Long accountId);
    void closeAccount(Long accountId);
}
