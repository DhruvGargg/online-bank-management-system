package org.example.onlinebankmanagementbackend.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequestDTO
{
    private TransactionType type;
    private TransactionStatus transactionStatus;
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;
    private String description;
    private Long transactionId;
    private String accountNumber;
    private String username;

}
