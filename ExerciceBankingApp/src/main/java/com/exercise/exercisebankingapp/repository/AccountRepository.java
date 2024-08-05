package com.exercise.exercisebankingapp.repository;

import com.exercise.exercisebankingapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("SELECT s FROM Account s WHERE s.accountNumber = ?1")
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    @Query("SELECT s FROM Account s WHERE s.user.id = ?1")
    List<Account> findAccountsByUserId(Long userId);

    @Query("SELECT s FROM Account s WHERE s.user.name = ?1")
    List<Account> findAccountsByUserName(String userName);

    @Query("SELECT s.balance FROM Account s WHERE s.id = ?1")
    double findAccountMoneyByAccountId(Long accountId);
}
