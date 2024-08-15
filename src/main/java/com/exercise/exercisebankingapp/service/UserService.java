package com.exercise.exercisebankingapp.service;

import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.exception.UserNotFoundException;
import com.exercise.exercisebankingapp.mapper.UserMapper;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final SecurityConfig securityConfig;

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.securityConfig = securityConfig;
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("MyUser not found with id: " + id));
    }

    public MyUser getUserByName(String name) {
        return userRepository.findUserByName(name).orElseThrow(() -> new UserNotFoundException("MyUser not found with name: " + name));
    }

    public MyUser getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("MyUser not found with email: " + email));
    }

    public List<Account> getUserAccounts(Long userId) {
        MyUser myUser = getUserById(userId);
        if (myUser != null) {
            return myUser.getAccounts();
        }
        return null;
    }

    public MyUser addUser(UserRegisterDTO userRegisterDTO) {
        MyUser myUser = new MyUser(
                userRegisterDTO.getName(),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getDateOfBirth(),
                userRegisterDTO.getAddress(),
                userRegisterDTO.getPhoneNumber(),
                userRegisterDTO.getRole(),
                securityConfig.passwordEncoder().encode(userRegisterDTO.getPassword()),
                userRegisterDTO.getStatus()
        );
        return userRepository.save(myUser);
    }

    public MyUser updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        MyUser existingMyUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("MyUser not found with id: " + userId));
        UserMapper.INSTANCE.updateUserFromDTO(userUpdateDTO, existingMyUser);
        return userRepository.save(existingMyUser);
    }

    public void deleteUserById(Long userId) {
        MyUser existingMyUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("MyUser not found with id: " + userId));
            userRepository.deleteById(userId);
    }

    public MyUser deleteUserByName(String userName) {
        MyUser myUser = userRepository.findUserByName(userName).orElse(null);
        if (myUser != null) {
            userRepository.delete(myUser);
        }
        return myUser;
    }

    public MyUser deleteUserByEmail(String userEmail) {
        MyUser myUser = userRepository.findUserByEmail(userEmail).orElse(null);
        if (myUser != null) {
            userRepository.delete(myUser);
        }
        return myUser;
    }

    public MyUser changeUserStatus(Long userId, MyUser.Status newStatus) {
        Optional<MyUser> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            MyUser myUser = userOptional.get();
            myUser.setStatus(newStatus);
            userRepository.save(myUser);
            for (Account account : myUser.getAccounts()) {
                account.setStatus(newStatus);
                accountRepository.save(account);
            }
            return myUser;
        } else {
            throw new UserNotFoundException("MyUser not found with id: " + userId);
        }
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
