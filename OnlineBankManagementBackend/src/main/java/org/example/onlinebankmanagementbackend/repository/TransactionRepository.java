package org.example.onlinebankmanagementbackend.repository;

import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    Optional<Transaction> getTransactionsByTransactionExternalId(String transactionExternalId);

    List<Transaction> findByAccountAndTransactionCreatedAtBetween(Account account, LocalDate transactionDateAfter, LocalDate transactionDateBefore);

    Optional<Transaction> findByTransactionId(Long transactionId);

    boolean existsByTransactionExternalId(String transactionExternalId);

    Account findByReferenceAccountNumber(String referenceAccountNumber);

}
