package com.exercise.exercisebankingapp.configuration;

import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository userRepository) {
        /*return args -> {
            MyUser myUser1 = new MyUser(
                    "John",
                    "John@gmail.com",
                    LocalDate.of(1990, 5, 31),
                    "44 Quai Charles-Pages",
                    "06 75 15 82 22",
                    MyUser.Role.USER,
                    "password",
                    MyUser.Status.ONBOARDING
                    );
            userRepository.save(myUser1);
            System.out.println("MyUser added");
        };*/
        return args -> {
        };
    }
}
