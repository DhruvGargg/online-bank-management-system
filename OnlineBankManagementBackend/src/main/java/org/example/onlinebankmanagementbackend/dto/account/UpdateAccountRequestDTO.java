package org.example.onlinebankmanagementbackend.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinebankmanagementbackend.enums.*;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateAccountRequestDTO
{
    private AccountStatus status;
    private AccountTheme theme;
    private AccountType type;
    private String description;
    private BigDecimal balance;
    private String accountHolderName;
    private String email;
    private String mobileNumber;
    private String password;
    private Long transactionId;
    private String accountNumber;
    private String username;

}
