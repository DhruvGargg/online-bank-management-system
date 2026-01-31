package org.example.onlinebankmanagementbackend.statement.service;

import lombok.Getter;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.example.onlinebankmanagementbackend.repository.AccountRepository;
import org.example.onlinebankmanagementbackend.repository.TransactionRepository;
import org.example.onlinebankmanagementbackend.service.impl.AccountOwnershipService;
import org.example.onlinebankmanagementbackend.statement.image.ImageGenerator;
import org.example.onlinebankmanagementbackend.statement.pdf.PdfGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Getter
@Service
@Transactional(readOnly = true)
public class StatementServiceImpl implements StatementService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountOwnershipService ownershipService;
    private final PdfGenerator pdfGenerator;
    private final ImageGenerator imageGenerator;

    public StatementServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            AccountOwnershipService ownershipService,
            PdfGenerator pdfGenerator,
            ImageGenerator imageGenerator
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.ownershipService = ownershipService;
        this.pdfGenerator = pdfGenerator;
        this.imageGenerator = imageGenerator;
    }

    @Override
    public byte[] generateAccountStatementPdf(
            Long accountId,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        Long userId = getCurrentUserId();

        Account account = ownershipService
                .validateOwnership(accountId, userId);

        List<Transaction> transactions =
                transactionRepository.findByAccountAndTransactionCreatedAtBetween(
                        account,
                        fromDate,
                        toDate
                );

        return pdfGenerator.generateAccountStatement(
                account,
                transactions
        );
    }

    public byte[] generateTransactionReceiptImage(Long transactionId,
                                                  LocalDate fromDate,
                                                  LocalDate toDate)
    {

        Long userId = getCurrentUserId();

        Transaction transaction = transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found")
                );

        Account fromAccount = transaction.getAccount();
        Account toAccount = transactionRepository.findByReferenceAccountNumber(transaction.getReferenceAccountNumber());
        if(!fromAccount.getUser().getUserId().equals(userId) || !toAccount.getUser().getUserId().equals(userId))
        {
            throw new RuntimeException("Access denied");
        }

        return imageGenerator.generateTransactionReceipt(transaction);
    }

    public byte[] generateAccountPdf(Long accountId,
                                     LocalDate fromDate,
                                     LocalDate toDate)
    {
        Long userId = Long.parseLong(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        Account account = ownershipService
                .validateOwnership(accountId, userId);

        List<Transaction> transactions =
                transactionRepository.findByAccountAndTransactionCreatedAtBetween(
                        account,
                        fromDate,
                        toDate
                );

        return pdfGenerator.generateAccountStatement(
                account,
                transactions
        );
    }

    private Long getCurrentUserId() {
        return Long.parseLong(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
    }
}
