package com.exercise.exercisebankingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "account")
@Getter
@Setter
public class Account {

    public enum AccountType {
        SAVINGS,
        PERSONAL,
        BUSINESS
    }

    public enum AccountStatus {
        ONBOARDING,
        ACTIVE,
        CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Account() {
        this.accountNumber = randomAccountNumber();
        this.balance = 0.0;
        this.accountStatus = AccountStatus.ONBOARDING;
        this.accountType = AccountType.PERSONAL;
    }

    private String randomAccountNumber() {
        return "AAFF" + (int) (Math.random() * 100000) + " " + (int) (Math.random() * 1000);
    }

    public Account(String accountNumber, AccountType accountType, Double balance, User user) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", user=" + user +
                '}';
    }

}


