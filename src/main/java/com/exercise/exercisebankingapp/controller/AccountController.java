package com.exercise.exercisebankingapp.controller;

import com.exercise.exercisebankingapp.dataTransferObject.MoneyRequest;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.exception.AccountNotFoundException;
import com.exercise.exercisebankingapp.exception.UserNotFoundException;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.service.AccountService;
import com.exercise.exercisebankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path ="/api/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AccountController(AccountService accountService, UserService userService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // find account with account id
    @GetMapping({"/{accountId}"})
    public Account getAccountById(@PathVariable Long accountId) {
        try {
            return accountService.getAccountById(accountId);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }

    // find account with account number
    /*
    /@GetMapping({"/{accountNumber}"})
    public Account getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }*/

    // find account list with user id
    @GetMapping({"/userId/{userId}"})
    public List<Account> getUserAccounts(@PathVariable Long userId) {
        try {
            return userService.getUserAccounts(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }

    /* find account list with the Username
    @GetMapping({"/userName/{userName}"})
    public List<Account> getUserAccounts(@PathVariable String userName) {
        try {
            return accountService.getUserAccountsByUserName(userName);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }*/

    // find money with account id
    @GetMapping({"/money/{accountId}"})
    public double getAccountMoney(@PathVariable Long accountId) {
        try {
            return accountService.getAccountMoneyByAccountId(accountId);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }

    // find total money with user id (sum of all account money)
    @GetMapping({"/allBalance/{userId}"})
    public double getUserTotalMoney(@PathVariable Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
        return getUserAccounts(userId).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }


    // PUT
    // add money to account with account id + request body
    @PutMapping({"/addMoney/{accountId}"})
    public double addMoney(@PathVariable Long accountId, @RequestBody MoneyRequest moneyRequest) throws Exception {
        try {
            return accountService.addMoney(accountId, moneyRequest);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }

    // withdraw money from account with account id + request body
    @PutMapping({"/withdrawMoney/{accountId}"})
    public double withdrawMoney(@PathVariable Long accountId, @RequestBody MoneyRequest moneyRequest) throws Exception {
        try {
            return accountService.withdrawMoney(accountId, moneyRequest);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }


    // transfer money from account with account id to account with account id + request body
    @PutMapping({"/transferMoney/{originAccountId}/{targetAccountId}"})
    public double transferMoney(@PathVariable Long originAccountId, @PathVariable Long targetAccountId, @RequestBody MoneyRequest moneyRequest) throws Exception {
        try {
            return accountService.transferMoney(originAccountId, targetAccountId, moneyRequest);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }

    // POST
    // open account with user id + body
    @PostMapping({"/openAccount/{userId}"})
    public Account openAccount(@PathVariable Long userId, @RequestBody Account account) {
        // VERIFY if user exists TO BE IMPLEMENTED
        try {
            return accountService.createAccount(account, userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "body invalid"
            );
        }
    }

    // DELETE
    // close account with account id
    @DeleteMapping({"/closeAccount/{accountId}"})
    public double closeAccount(@PathVariable Long accountId) {
        try {
            return accountService.deleteAccount(accountId);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found"
            );
        }
    }

    // find account with account id
    // find account with account number
    // find account list with user id
    // find account list with the Username
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

