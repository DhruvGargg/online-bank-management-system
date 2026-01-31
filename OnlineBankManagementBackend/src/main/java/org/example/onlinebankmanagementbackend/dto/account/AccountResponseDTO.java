package org.example.onlinebankmanagementbackend.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinebankmanagementbackend.entity.User;
import org.example.onlinebankmanagementbackend.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResponseDTO
{
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private AccountStatus status;
    private AccountTheme theme;
    private String description;
    private LocalDateTime createdAt;
    private String accountHolderName;
    private String email;
    private String mobileNumber;
    private String currency;
    private User user;
    private Long userId;
    private String username;
    private String role;
    private Long transactionId;

}
