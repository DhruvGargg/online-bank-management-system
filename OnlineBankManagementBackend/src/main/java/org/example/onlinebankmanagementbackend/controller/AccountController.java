package org.example.onlinebankmanagementbackend.controller;

import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.example.onlinebankmanagementbackend.dto.account.CreateAccountRequestDTO;
import org.example.onlinebankmanagementbackend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(
            @RequestBody CreateAccountRequestDTO requestDTO)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(requestDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountResponseDTO>> getMyAccounts()
    {
        return ResponseEntity.ok(accountService.getMyAccounts());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(
            @PathVariable Long accountId)
    {
        return ResponseEntity.ok(
                accountService.getAccountByAccountId(accountId)
        );
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> closeAccount(
            @PathVariable Long accountId)
    {
        accountService.closeAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
