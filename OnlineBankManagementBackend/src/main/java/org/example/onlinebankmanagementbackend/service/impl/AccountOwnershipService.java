package org.example.onlinebankmanagementbackend.service.impl;

import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountOwnershipService {
    private final AccountRepository accountRepository;
    public AccountOwnershipService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Account validateOwnership(Long accountId, Long userId)
    {
        return accountRepository
                .findByAccountIdAndUser_UserId(accountId, userId)
                .orElseThrow(() -> new RuntimeException("Invalid account or user"));
    }
}
