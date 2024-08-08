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

    public void setStatus(MyUser.Status newStatus) {
        this.accountStatus = newStatus;
    }

    public enum AccountType {
        SAVINGS,
        PERSONAL,
        BUSINESS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private MyUser.Status accountStatus;
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private MyUser myUser;

    public Account() {
        this.accountNumber = randomAccountNumber();
        this.balance = 0.0;
        this.accountStatus = MyUser.Status.ONBOARDING;
        this.accountType = AccountType.PERSONAL;
    }

    public Account(MyUser myUser) {
        this.accountNumber = randomAccountNumber();
        this.myUser = myUser;
        this.balance = 0.0;
        this.accountType = AccountType.PERSONAL;
        this.accountStatus = myUser.getStatus();
    }

    public Account(MyUser myUser, double balance) {
        this.accountNumber = randomAccountNumber();
        this.myUser = myUser;
        this.balance = balance;
        this.accountType = AccountType.PERSONAL;
        this.accountStatus = myUser.getStatus();
    }

    private String randomAccountNumber() {
        return "AAFF" + (int) (Math.random() * 100000) + " " + (int) (Math.random() * 1000);
    }

    public Account(String accountNumber, AccountType accountType, Double balance, MyUser myUser) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.myUser = myUser;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", MyUser=" + myUser +
                '}';
    }

}


