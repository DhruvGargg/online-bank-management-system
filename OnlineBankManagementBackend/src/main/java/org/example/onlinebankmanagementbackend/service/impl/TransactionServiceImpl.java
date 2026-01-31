package org.example.onlinebankmanagementbackend.service.impl;

import org.example.onlinebankmanagementbackend.dto.transaction.CreateTransactionRequestDTO;
import org.example.onlinebankmanagementbackend.dto.transaction.TransactionResponseDTO;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.example.onlinebankmanagementbackend.enums.AccountStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;
import org.example.onlinebankmanagementbackend.exception.InsufficientBalanceException;
import org.example.onlinebankmanagementbackend.exception.ResourceNotFoundException;
import org.example.onlinebankmanagementbackend.mapper.TransactionMapper;
import org.example.onlinebankmanagementbackend.notification.service.EmailService;
import org.example.onlinebankmanagementbackend.notification.util.EmailTemplateUtil;
import org.example.onlinebankmanagementbackend.repository.AccountRepository;
import org.example.onlinebankmanagementbackend.repository.TransactionRepository;
import org.example.onlinebankmanagementbackend.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;


    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public TransactionResponseDTO debit(CreateTransactionRequestDTO requestDTO) {
        Account account = accountRepository
                .findByAccountNumberForUpdate(requestDTO.getFromAccount().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        validateAccount(account, requestDTO.getAmount());

        account.setBalance(account.getBalance().subtract(requestDTO.getAmount()));
        Transaction transaction = new Transaction();
        do
        {
            transaction.setTransactionExternalId(generateTransactionExternalId());
        }
        while(transactionRepository.existsByTransactionExternalId(transaction.getTransactionExternalId()));
        transaction.setTransactionId(requestDTO.getTransactionId());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionCreatedAt(LocalDateTime.now());
        transaction.setReferenceAccountNumber(requestDTO.getAccountNumber());
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setAccount(account);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        emailService.sendTransactionEmail(
                account.getUser().getEmail(),
                "Debit Alert – Your Bank",
                EmailTemplateUtil.debitEmail(
                        account.getUser().getName(),
                        account.getAccountNumber(),
                        account.getBalance()
                )
        );
        return TransactionMapper.mapToTransactionResponseDTO(transaction);
    }

    @Transactional
    @Override
    public TransactionResponseDTO credit(CreateTransactionRequestDTO requestDTO) {
        Account account = accountRepository.findByAccountNumberForUpdate(requestDTO.getToAccount().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(requestDTO.getAmount()));
        Transaction transaction = new Transaction();
        do
        {
            transaction.setTransactionExternalId(generateTransactionExternalId());
        }
        while(transactionRepository.existsByTransactionExternalId(transaction.getTransactionExternalId()));
        transaction.setTransactionId(requestDTO.getTransactionId());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionCreatedAt(LocalDateTime.now());
        transaction.setReferenceAccountNumber(requestDTO.getAccountNumber());
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setAccount(account);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        emailService.sendTransactionEmail(
                account.getUser().getEmail(),
                "Credit Alert – Your Bank",
                EmailTemplateUtil.creditEmail(
                        account.getUser().getName(),
                        account.getAccountNumber(),
                        requestDTO.getAmount(),
                        account.getBalance()
                )
        );
        return TransactionMapper.mapToTransactionResponseDTO(transaction);
    }

    @Transactional
    @Override
    public TransactionResponseDTO transfer(CreateTransactionRequestDTO requestDTO) {
        Account senderAccount = accountRepository.findByAccountNumberForUpdate(requestDTO.getFromAccount().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));
        Account receiverAccount = accountRepository.findByAccountNumberForUpdate(requestDTO.getToAccount().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));
        if(senderAccount.getAccountNumber().equals(receiverAccount.getAccountNumber()))
        {
            throw new IllegalArgumentException("Sender and receiver cannot be same account");
        }
        validateAccount(senderAccount, requestDTO.getAmount());
        senderAccount.setBalance(senderAccount.getBalance().subtract(requestDTO.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(requestDTO.getAmount()));
        Transaction transaction = buildTransaction(senderAccount, receiverAccount, requestDTO);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO getTransactionsByTransactionExternalId(String transactionExternalId) {
        Transaction transaction = transactionRepository.getTransactionsByTransactionExternalId(transactionExternalId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return TransactionMapper.mapToTransactionResponseDTO(transaction);
    }


    @Override
    public Page<TransactionResponseDTO> getTransactionsByAccount(String accountNumber,
                                                                 int page,
                                                                 int size)
    {
        Specification<Transaction> specification = Specification.where(TransactionSpecification.hasAccount(accountNumber));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);
        return transactions.map(TransactionMapper::mapToTransactionResponseDTO);
    }

    @Override
    public Page<TransactionResponseDTO> getTransactions(String accountNumber,
                                                                  TransactionType type,
                                                                  TransactionStatus status,
                                                                  LocalDate from,
                                                                  LocalDate to,
                                                                  int page,
                                                                  int size)
    {
        Specification<Transaction> specification =
                Specification.where(TransactionSpecification.hasAccount(accountNumber))
                        .and(TransactionSpecification.createdBetween(from, to))
                        .and(TransactionSpecification.hasType(type))
                        .and(TransactionSpecification.hasStatus(status));
        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").descending());
        Page<Transaction> transactions = transactionRepository.findAll(specification, pageable);
        return transactions.map(TransactionMapper::mapToTransactionResponseDTO);
    }

    private void validateAccount(Account account,
                                 BigDecimal amount)
    {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
    }

    private String generateTransactionExternalId()
    {
        final String PREFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        for(int i = 0; i < 20; i++)
        {
            sb.append(PREFIX.charAt(RANDOM.nextInt(PREFIX.length())));
        }
        return sb.toString();
    }

    private Transaction buildTransaction(Account senderAccount,
                                         Account receiverAccount,
                                         CreateTransactionRequestDTO requestDTO)
    {
        Transaction transaction = new Transaction();
        do
        {
            transaction.setTransactionExternalId(generateTransactionExternalId());
        }
        while(transactionRepository.existsByTransactionExternalId(transaction.getTransactionExternalId()));
        transaction.setTransactionId(requestDTO.getTransactionId());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionCreatedAt(LocalDateTime.now());
        transaction.setReferenceAccountNumber(receiverAccount.getAccountNumber());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAmount(requestDTO.getAmount());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setAccount(senderAccount);
        return transaction;
    }
}
