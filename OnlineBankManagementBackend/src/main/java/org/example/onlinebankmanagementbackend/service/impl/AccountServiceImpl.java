package org.example.onlinebankmanagementbackend.service.impl;

import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.example.onlinebankmanagementbackend.dto.account.CreateAccountRequestDTO;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.entity.User;
import org.example.onlinebankmanagementbackend.enums.AccountStatus;
import org.example.onlinebankmanagementbackend.enums.AccountTheme;
import org.example.onlinebankmanagementbackend.mapper.AccountMapper;
import org.example.onlinebankmanagementbackend.repository.AccountRepository;
import org.example.onlinebankmanagementbackend.repository.UserRepository;
import org.example.onlinebankmanagementbackend.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.onlinebankmanagementbackend.security.util.SecurityUtil.getCurrentUserId;

@Service
@Transactional
public class AccountServiceImpl implements AccountService
{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountOwnershipService accountOwnershipService;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountOwnershipService accountOwnershipService)
    {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountOwnershipService = accountOwnershipService;
    }

    @Override
    public AccountResponseDTO createAccount(CreateAccountRequestDTO requestDTO)
    {
        Long userId = requestDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(requestDTO.getType());
        account.setAccountTheme(requestDTO.getTheme());
        account.setAccountCreatedAt(LocalDateTime.now());
        account.setUser(user);

        accountRepository.save(account);
        return AccountMapper.mapToAccountResponseDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getMyAccounts() {
        Long userId = getCurrentUserId();

        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByAccountId(Long accountId) {
        Long userId = getCurrentUserId();
        Account account = accountOwnershipService
                .validateOwnership(accountId, userId);
        return mapToResponse(account);
    }

    @Override
    public void closeAccount(Long accountId)
    {
        Long userId = getCurrentUserId();

        Account account = accountOwnershipService
                .validateOwnership(accountId, userId);
        if(!account.getBalance().equals(BigDecimal.ZERO))
        {
            throw new IllegalStateException("Account balance is not zero");
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
        System.out.println("Account closed successfully");
    }

    public void freezeAccount(String accountNumber)
    {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setAccountStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
        System.out.println("Account frozen successfully");
    }

    private AccountResponseDTO mapToResponse(Account account)
    {

        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setType(account.getAccountType());
        dto.setStatus(account.getAccountStatus());
        dto.setBalance(account.getBalance());
        dto.setTheme(account.getAccountTheme());
        dto.setCreatedAt(account.getAccountCreatedAt());
        return dto;
    }

    public void unfreezeAccount(String accountNumber)
    {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        System.out.println("Account unfrozen successfully");
    }

    public AccountResponseDTO updateAccountTheme(String accountNumber, AccountTheme theme, String description)
    {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setAccountTheme(theme);
        accountRepository.save(account);
        return AccountMapper.mapToAccountResponseDTO(account);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return AccountMapper.mapToAccountResponseDTO(account);
    }

    private static String generateAccountNumber()
    {
        long number = (long) (Math.random() * 1_000_000_000_000L);
        return String.format("%012d", number);
    }

}
