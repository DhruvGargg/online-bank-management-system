package org.example.onlinebankmanagementbackend.statement.service;


import java.time.LocalDate;

public interface StatementService {

    byte[] generateAccountStatementPdf(Long accountId,
                                       LocalDate fromDate,
                                       LocalDate toDate
    );

    byte[] generateTransactionReceiptImage(Long transactionId,
                                           LocalDate fromDate,
                                           LocalDate toDate

    );

    byte[] generateAccountPdf(Long accountId,
                              LocalDate fromDate,
                              LocalDate toDate);
}
