package org.example.onlinebankmanagementbackend.mapper;

import org.example.onlinebankmanagementbackend.dto.transaction.TransactionResponseDTO;
import org.example.onlinebankmanagementbackend.entity.Transaction;

public class TransactionMapper
{
    public static TransactionResponseDTO mapToTransactionResponseDTO(Transaction transaction)
    {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setTransactionId(transaction.getTransactionId());
        transactionResponseDTO.setType(transaction.getTransactionType());
        transactionResponseDTO.setStatus(transaction.getTransactionStatus());
        transactionResponseDTO.setAmount(transaction.getAmount());
        transactionResponseDTO.setDescription(transaction.getDescription());
        transactionResponseDTO.setCreatedAt(transaction.getTransactionCreatedAt());

        if (transaction.getAccount() != null && transaction.getAccount().getUser() != null)
        {
            transactionResponseDTO.setAccountHolderName(transaction.getAccount().getUser().getName());
            transactionResponseDTO.setEmail(transaction.getAccount().getUser().getEmail());
            transactionResponseDTO.setMobileNumber(transaction.getAccount().getUser().getMobileNumber());
        }

        return transactionResponseDTO;
    }
}
