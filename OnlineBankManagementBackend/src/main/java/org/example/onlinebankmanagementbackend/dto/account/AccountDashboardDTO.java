package org.example.onlinebankmanagementbackend.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinebankmanagementbackend.enums.AccountStatus;
import org.example.onlinebankmanagementbackend.enums.AccountTheme;
import org.example.onlinebankmanagementbackend.enums.AccountType;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDashboardDTO
{
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private AccountStatus status;
    private AccountTheme theme;
    private String description;
    private String accountHolderName;
    private String email;
    private String mobileNumber;
    private String currency;
    private String createdAt;

}
