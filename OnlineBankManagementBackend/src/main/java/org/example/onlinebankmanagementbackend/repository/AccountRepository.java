package org.example.onlinebankmanagementbackend.repository;

import jakarta.persistence.LockModeType;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByAccountId(Long accountId);

    Optional<Account> findByAccountIdAndUser_UserId(Long accountId,
                                               Long userId);

    List<Account> findAllByUser_UserId(Long userId);

    @Query("SELECT a FROM Account a WHERE a.user.userId = :userId")
    List<Account> findAllByUserId(@Param("userId") Long userId);

    Optional<Account> findByAccountNumberAndUser_UserId(String accountNumber,
                                                   Long userId);

}