//package com.exercise.exercisebankingapp.entity;
//
//import com.exercise.exercisebankingapp.repository.AccountRepository;
//import jakarta.persistence.PreUpdate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserEntityListener {
//
//    private static AccountRepository accountRepository;
//
//    @Autowired
//    public void setAccountRepository(AccountRepository accountRepository) {
//        UserEntityListener.accountRepository = accountRepository;
//    }
//
//    @PreUpdate
//    public void preUpdate(MyUser user) {
//        for (Account account : user.getAccounts()) {
//            account.setAccountStatus(user.getStatus());
//            accountRepository.save(account);
//        }
//    }
//}
