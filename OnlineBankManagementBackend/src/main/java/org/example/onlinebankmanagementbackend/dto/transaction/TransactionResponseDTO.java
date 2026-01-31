package org.example.onlinebankmanagementbackend.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDTO
{
    private Long transactionId;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private LocalDateTime createdAt;
    private String accountHolderName;
    private String email;
    private String mobileNumber;

}
