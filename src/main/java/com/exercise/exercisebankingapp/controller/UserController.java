package com.exercise.exercisebankingapp.controller;

import com.exercise.exercisebankingapp.dataTransferObject.AuthenticationRequest;
import com.exercise.exercisebankingapp.dataTransferObject.AuthenticationResponse;
import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.exception.UserNotFoundException;
import com.exercise.exercisebankingapp.security.util.JwtUtil;
import com.exercise.exercisebankingapp.service.MyUserDetailsService;
import com.exercise.exercisebankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
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

//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getName(), authenticationRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
//        }
//        final UserDetails userDetails = myUserDetailsService
//                .loadUserByUsername(authenticationRequest.getName());
//        final String jwt = jwtUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }

    //@PostMapping("/login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getName(), authenticationRequest.getPassword())
            );

            // Check if authentication is successful
            if (authentication.isAuthenticated()) {
                final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getName());
                final String jwt = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Bad credentials"
                );

            }
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Incorrect username or password"
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred"
            );
        }
    }


}
