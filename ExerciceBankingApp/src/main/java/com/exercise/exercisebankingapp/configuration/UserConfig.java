package com.exercise.exercisebankingapp.configuration;

import com.exercise.exercisebankingapp.entity.User;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository userRepository) {
        return args -> {
            User user1 = new User(
                    "John",
                    "John@gmail.com",
                    LocalDate.of(1990, 5, 31),
                    "44 Quai Charles-Pages",
                    "06 75 15 82 22"
            );
            userRepository.save(user1);
            System.out.println("User added");
        };
    }
}
