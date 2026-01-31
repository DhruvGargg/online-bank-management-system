package org.example.onlinebankmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = "transactionExternalId")
)
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false, unique = true)
    private String transactionExternalId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private String referenceAccountNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    private LocalDateTime transactionCreatedAt = LocalDateTime.now();
}
