package com.exercise.exercisebankingapp.service;

import com.exercise.exercisebankingapp.dataTransferObject.MoneyRequest;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.exception.AccountNotFoundException;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final View error;
    private final UserRepository userRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, View error, UserRepository userRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.error = error;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("account not found with id: " + accountId));
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("account not found with AccountNumber: " + accountNumber));
    }

    public List<Account> getUserAccountsByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId);

    }

    public List<Account> getUserAccountsByUserName(String userName) {
        MyUser myUser = userService.getUserByName(userName);
        if (myUser != null) {
            return myUser.getAccounts();
        }
        return null;
    }

    public double getAccountMoneyByAccountId(Long accountId) {
        return accountRepository.findById(accountId)
                .map(Account::getBalance)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));
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
         Optional<MyUser> optionalUser = userRepository.findById(userId);
         if (optionalUser.isPresent()) {
             account.setMyUser(optionalUser.get());
             return accountRepository.save(account);
         } else {
             throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "MyUser not found"
             );
         }
    }
    
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public double deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow (() -> new AccountNotFoundException("account not found with id: " + accountId));
        if (account == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
        double balance = account.getBalance();
        accountRepository.deleteById(accountId);
        return balance;
    }

    public double getUserTotalMoney(Long userId) {
        List<Account> accounts = getUserAccountsByUserId(userId);
        double totalMoney = 0;
        for (Account account : accounts) {
            totalMoney += account.getBalance();
        }
        return totalMoney;
    }

    public double addMoney(Long accountId, MoneyRequest moneyRequest) throws Exception {
        Account account = getAccountById(accountId);
        MyUser.Status status = account.getAccountStatus();
        System.out.println("status: " + status);
        if (status == MyUser.Status.ACTIVE) {
            account.setBalance(account.getBalance() + moneyRequest.getMoney());
            accountRepository.save(account);
            return account.getBalance();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Account is not active"
            );
        }
    }

    public double withdrawMoney(Long accountId, MoneyRequest moneyRequest) throws Exception {
        Account account = getAccountById(accountId);
        // VERIFY if origin account balance TO BE IMPLEMENTED
        MyUser.Status status = account.getAccountStatus();
        if (status == MyUser.Status.ACTIVE) {
            if (account.getBalance() >= moneyRequest.getMoney()) {
                account.setBalance(account.getBalance() - moneyRequest.getMoney());
                accountRepository.save(account);
                return account.getBalance();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient funds"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account is not active"
            );
        }
    }

    public double transferMoney(Long originAccountId, Long targetAccountId, MoneyRequest moneyRequest) throws Exception {
        Account originAccount = getAccountById(originAccountId);
        Account targetAccount = getAccountById(targetAccountId);
        // VERIFY if origin account balance TO BE IMPLEMENTED
        MyUser.Status status = originAccount.getAccountStatus();
        if (status == MyUser.Status.ACTIVE) {
            if (originAccount.getBalance() >= moneyRequest.getMoney()) {
                originAccount.setBalance(originAccount.getBalance() - moneyRequest.getMoney());
                targetAccount.setBalance(targetAccount.getBalance() + moneyRequest.getMoney());
                accountRepository.save(originAccount);
                accountRepository.save(targetAccount);
                return originAccount.getBalance();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient funds"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Account is not active"
            );
        }
    }
}
