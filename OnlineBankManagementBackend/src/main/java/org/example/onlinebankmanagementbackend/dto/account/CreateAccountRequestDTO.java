package org.example.onlinebankmanagementbackend.dto.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.example.onlinebankmanagementbackend.enums.*;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequestDTO
{
    private Long userId;
    @NotNull(message = "Account type is required")
    private AccountType type;
    @NotNull(message = "Account theme is required")
    private AccountTheme theme;
    @NotNull(message = "Balance is required")
    @Positive(message = "Balance must be greater than 0")
    private BigDecimal balance;
    private String description;
    @NotNull(message = "Account holder name is required")
    private String accountHolderName;
    @NotNull(message = "Email is required")
    private String email;
    private String mobileNumber;
    @NotNull(message = "Password is required")
    private String password;

}