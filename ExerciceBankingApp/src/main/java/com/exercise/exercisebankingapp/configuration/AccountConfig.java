package com.exercise.exercisebankingapp.configuration;

import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.User;
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

        User user2 = new User(
                "elie",
                "elie@gmail.com",
                LocalDate.of(2004, 5, 31),
                "44 Quai Charles-Pages",
                "06 75 15 89 32");
        userRepository.save(user2);
        return args -> {
            Account account1 = new Account(
                    "AAFF676414111 331",
                    Account.AccountType.PERSONAL,
                    1000.0,
                    user2
            );
            accountRepository.save(account1);
            user2.setAccounts(List.of(account1));
            System.out.println("Account added");
        };
    }
}
