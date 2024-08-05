package com.exercise.exercisebankingapp.controller;

import com.exercise.exercisebankingapp.dataTransferObject.MoneyRequest;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.mapper.MoneyRequestMapper;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/api/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // find account with account id
    @GetMapping({"/{accountId}"})
    public Account getAccountById(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId);
    }

    // find account with account number
    /*@GetMapping({"/{accountNumber}"})
    public Account getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }*/

    // find account list with user id
    @GetMapping({"/userId/{userId}"})
    public List<Account> getUserAccounts(@PathVariable Long userId) {
        return accountService.getUserAccountsByUserId(userId);
    }

    // find account list with the User name
    @GetMapping({"/userName/{userName}"})
    public List<Account> getUserAccounts(@PathVariable String userName) {
        return accountService.getUserAccountsByUserName(userName);
    }

    // find money with account id
    @GetMapping({"/money/{accountId}"})
    public double getAccountMoney(@PathVariable Long accountId) {
        return accountService.getAccountMoneyByAccountId(accountId);
    }

    // find total money with user id (sum of all account money)
    @GetMapping({"/allBalance/{userId}"})
    public double getUserTotalMoney(@PathVariable Long userId) {
        List<Account> accounts = accountService.getUserAccountsByUserId(userId);
        double totalMoney = 0;
        for (Account account : accounts) {
            totalMoney += account.getBalance();
        }
        return totalMoney;
    }


    // PUT
    // add money to account with account id + request body
    @PutMapping({"/addMoney/{accountId}"})
    public double addMoney(@PathVariable Long accountId, @RequestBody MoneyRequest moneyRequest) {
        Account account = accountService.getAccountById(accountId);
        account.setBalance(account.getBalance() + moneyRequest.getMoney());
        accountRepository.save(account);
        return account.getBalance();
    }

    // withdraw money from account with account id + request body
    @PutMapping({"/withdrawMoney/{accountId}"})
    public double withdrawMoney(@PathVariable Long accountId, @RequestBody MoneyRequest moneyRequest) {
        Account account = accountService.getAccountById(accountId);
        // VERIFY if origin account balance TO BE IMPLEMENTED
        account.setBalance(account.getBalance() - moneyRequest.getMoney());
        accountRepository.save(account);
        return account.getBalance();
    }

    // transfer money from account with account id to account with account id + request body
    @PutMapping({"/transferMoney/{originAccountId}/{targetAccountId}"})
    public double transferMoney(@PathVariable Long originAccountId, @PathVariable Long targetAccountId, @RequestBody MoneyRequest moneyRequest) {
        Account originAccount = accountService.getAccountById(originAccountId);
        Account targetAccount = accountService.getAccountById(targetAccountId);
        // VERIFY if origin account balance TO BE IMPLEMENTED
        originAccount.setBalance(originAccount.getBalance() - moneyRequest.getMoney());
        targetAccount.setBalance(targetAccount.getBalance() + moneyRequest.getMoney());
        accountRepository.save(originAccount);
        accountRepository.save(targetAccount);
        return originAccount.getBalance();
    }

    // POST
    // open account with user id + body
    @PostMapping({"/openAccount/{userId}"})
    public Account openAccount(@PathVariable Long userId, @RequestBody Account account) {
        // VERIFY if user exists TO BE IMPLEMENTED
        return accountService.createAccount(account, userId);
    }

    // DELETE
    // close account with account id
    @DeleteMapping({"/closeAccount/{accountId}"})
    public double closeAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId);
    }

    // find account with account id
    // find account with account number
    // find account list with user id
    // find account list with the User name
    // find money with account id
    // Find account money with account number
    // find total money with user id (sum of all account money)
    // PUT
    // add money to account with account id + request body
    // withdraw money from account with account id + request body
    // transfer money from account with account id to account with account id + request body
    // POST
    // open account with user id + body
    // DELETE
    // close account with account id
}

