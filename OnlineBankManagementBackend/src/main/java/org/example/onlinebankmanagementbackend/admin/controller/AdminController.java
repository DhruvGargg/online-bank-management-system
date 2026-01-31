package org.example.onlinebankmanagementbackend.admin.controller;

import org.example.onlinebankmanagementbackend.admin.service.AdminService;
import org.example.onlinebankmanagementbackend.dto.account.AccountResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    private final AdminService adminService;
    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts()
    {
        return ResponseEntity.ok(adminService.getAllAccounts());
    }

    @PutMapping("/accounts/{accountId}/freeze" )
    public ResponseEntity<Void> freezeAccount(
            @PathVariable Long accountId
    )
    {
        adminService.freezeAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/accounts/{accountId}/activate")
    public ResponseEntity<Void> activateAccount(
            @PathVariable Long accountId
    )
    {
        adminService.activateAccount(accountId);
        return ResponseEntity.ok().build();
    }
}
