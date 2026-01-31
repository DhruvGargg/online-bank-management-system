package org.example.onlinebankmanagementbackend.mapper;

import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.example.onlinebankmanagementbackend.entity.Account;

public class AccountMapper
{
    public static AccountResponseDTO mapToAccountResponseDTO(Account account)
    {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setUserId(account.getUser().getUserId());
        accountResponseDTO.setAccountNumber(account.getAccountNumber());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setStatus(account.getAccountStatus());
        accountResponseDTO.setTheme(account.getAccountTheme());
        accountResponseDTO.setType(account.getAccountType());
        accountResponseDTO.setCreatedAt(account.getAccountCreatedAt());
        accountResponseDTO.setAccountHolderName(account.getUser().getName());
        accountResponseDTO.setEmail(account.getUser().getEmail());
        accountResponseDTO.setMobileNumber(account.getUser().getMobileNumber());
        return accountResponseDTO;
    }
}
