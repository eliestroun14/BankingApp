package com.exercise.exercisebankingapp.service;

import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.User;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final View error;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, View error, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.error = error;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber).orElse(null);
    }

    public List<Account> getUserAccountsByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId);

    }

    public List<Account> getUserAccountsByUserName(String userName) {
        return accountRepository.findAccountsByUserName(userName);
    }

    public double getAccountMoneyByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            return account.getBalance();
        }
        System.err.println("Account not found");
        return -84;
    }

    public double getAccountMoneyByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElse(null);
        if (account != null) {
            return account.getBalance();
        }
        System.err.println("Account not found");
        return -84;
    }

    // ASK CHUCK
    public Account createAccount(Account account, Long userId) {
         Optional<User> optionalUser = userRepository.findById(userId);
         if (optionalUser.isPresent()) {
             account.setUser(optionalUser.get());
             return accountRepository.save(account);
         } else {
             System.err.println("User not found");
             return null;
         }
    }

    public double deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            System.err.println("account with id " + accountId + " does not exist");
            return -84;
        }
        double balance = account.getBalance();
        accountRepository.deleteById(accountId);
        return balance;
    }
}
