package org.example.onlinebankmanagementbackend.service.impl;

import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.example.onlinebankmanagementbackend.enums.TransactionStatus;
import org.example.onlinebankmanagementbackend.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionSpecification
{
    public static Specification<Transaction> hasAccount(String accountNumber)
    {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("senderAccountNumber"), accountNumber),
                        criteriaBuilder.equal(root.get("receiverAccountNumber"), accountNumber)
                );
    }

    public static Specification<Transaction> hasType(TransactionType type)
    {
        return (root, query, criteriaBuilder) ->
        {
            if (type == null)
            {
                return null;
            }
            return criteriaBuilder.equal(root.get("transactionType"), type);
        };
    }

    public static Specification<Transaction> hasStatus(TransactionStatus status)
    {
        return (root, query, criteriaBuilder) ->
        {
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("transactionStatus"), status);
        };
    }

    public static Specification<Transaction> createdBetween(LocalDate from, LocalDate to)
    {
        return (root, query, criteriaBuilder) ->
        {
            if (from == null && to == null)
            {
                return null;
            }
            if(from != null && to != null)
            {
                return criteriaBuilder.between(root.get("createdAt"), from, to);
            }
            if(from != null)
            {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
            }
            else
            {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
            }
        };
    }
}
