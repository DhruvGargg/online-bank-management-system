package org.example.onlinebankmanagementbackend.statement.controller;

import org.example.onlinebankmanagementbackend.statement.service.StatementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

public class StatementController
{
    private final StatementService statementService;
    public StatementController(StatementService statementService)
    {
        this.statementService = statementService;
    }

    @GetMapping("/accounts/{accountId}/statement/pdf")
    public ResponseEntity<byte[]> downloadAccountStatementPdf(
            @PathVariable Long accountId,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        byte[] pdfBytes = statementService.generateAccountPdf(accountId, fromDate, toDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=account_statement.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/transactions/{txnId}/receipt/png")
    public ResponseEntity<byte[]> downloadTransactionReceipt(
            @PathVariable Long transactionId,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        byte[] image = statementService.generateTransactionReceiptImage(transactionId, fromDate, toDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=transaction.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }


}