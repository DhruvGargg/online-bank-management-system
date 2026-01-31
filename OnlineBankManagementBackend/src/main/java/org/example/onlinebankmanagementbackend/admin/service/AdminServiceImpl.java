package org.example.onlinebankmanagementbackend.admin.service;

import jakarta.transaction.Transactional;
import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.enums.AccountStatus;
import org.example.onlinebankmanagementbackend.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService
{
    private final AccountRepository accountRepository;

    public AdminServiceImpl(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public List<AccountResponseDTO> getAllAccounts()
    {
        return accountRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void freezeAccount(Long accountId)
    {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getAccountStatus() == AccountStatus.CLOSED)
        {
            throw new RuntimeException("Account is already closed");
        }
        account.setAccountStatus(AccountStatus.INACTIVE);
    }

    @Override
    public void activateAccount(Long accountId)
    {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getAccountStatus() == AccountStatus.CLOSED)
        {
            throw new RuntimeException("Account is already closed");
        }
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    private AccountResponseDTO mapToResponse(Account account)
    {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setEmail(account.getUser().getEmail());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setCreatedAt(account.getAccountCreatedAt());
        accountResponseDTO.setAccountNumber(account.getAccountNumber());
        accountResponseDTO.setStatus(account.getAccountStatus());
        accountResponseDTO.setAccountHolderName(account.getUser().getName());
        accountResponseDTO.setTheme(account.getAccountTheme());
        accountResponseDTO.setType(account.getAccountType());
        accountResponseDTO.setMobileNumber(account.getUser().getMobileNumber());

        return accountResponseDTO;
    }
}
