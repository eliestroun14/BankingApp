package com.example.testingweb;

import static org.assertj.core.api.Assertions.assertThat;

import com.exercise.exercisebankingapp.ExerciseBankingAppApplication;
import com.exercise.exercisebankingapp.controller.AccountController;
import com.exercise.exercisebankingapp.controller.UserController;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.service.AccountService;
import com.exercise.exercisebankingapp.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = ExerciseBankingAppApplication.class
)
class ContextLoadsTest {

    @Autowired
    private UserController userController;
    @Autowired
    private AccountController accountController;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(accountController).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(accountRepository).isNotNull();
    }

}