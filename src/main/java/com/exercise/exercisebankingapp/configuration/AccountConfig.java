package com.exercise.exercisebankingapp.configuration;

import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    CommandLineRunner accountCommandLineRunner(AccountRepository accountRepository, UserRepository userRepository) {

        /*MyUser myUser2 = new MyUser(
                "elie",
                "elie@gmail.com",
                LocalDate.of(2004, 5, 31),
                "44 Quai Charles-Pages",
                "06 75 15 89 32",
                MyUser.Role.USER,
                "password",
                MyUser.Status.ONBOARDING
        );
        /*userRepository.save(myUser2);
        return args -> {
            Account account1 = new Account(
                    myUser2,
            1000.0
            );
            accountRepository.save(account1);
            myUser2.setAccounts(List.of(account1));
            System.out.println("Account added");
        };*/
        return args -> {
        };
    }
}
