package org.example.onlinebankmanagementbackend.service;

import org.example.onlinebankmanagementbackend.dto.transaction.CreateTransactionRequestDTO;
import org.example.onlinebankmanagementbackend.dto.transaction.TransactionResponseDTO;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TransactionService
{
    TransactionResponseDTO debit(CreateTransactionRequestDTO requestDTO);
    TransactionResponseDTO credit(CreateTransactionRequestDTO requestDTO);
    TransactionResponseDTO transfer(CreateTransactionRequestDTO requestDTO);
    TransactionResponseDTO getTransactionsByTransactionExternalId(String transactionExternalId);
    Page<TransactionResponseDTO> getTransactionsByAccount(String accountNumber,
                                                          int page,
                                                          int size);
    Page<TransactionResponseDTO> getTransactions(String accountNumber,
                                                 TransactionType type,
                                                 TransactionStatus status,
                                                 LocalDate fromDate,
                                                 LocalDate toDate,
                                                 int page,
                                                 int size);
}
