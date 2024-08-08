package com.exercise.exercisebankingapp.controller;

import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.exception.UserNotFoundException;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.service.AccountService;
import com.exercise.exercisebankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, AccountService accountService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<MyUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public MyUser getUserById(@PathVariable Long userId) {
        try {
            return userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }

    @GetMapping("/accounts/{userId}")
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

    @PostMapping("/register")
    public MyUser addUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        if (userService.userExistsByEmail(userRegisterDTO.getEmail())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "MyUser with email already exists"
                );
        }
        // myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return userService.addUser(userRegisterDTO);
    }

    @PutMapping("/{userId}")
    public MyUser updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            return userService.updateUser(userId, userUpdateDTO);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }

    @PutMapping("/{userId}/status")
    public MyUser changeUserStatus(
            @PathVariable Long userId,
            @RequestParam MyUser.Status newStatus) {
        try {
            return userService.changeUserStatus(userId, newStatus);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "MyUser not found"
            );
        }
    }


}
