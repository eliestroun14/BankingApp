package com.exercise.exercisebankingapp.service;

import com.exercise.exercisebankingapp.dataTransferObject.UserUpdateDTO;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.User;
import com.exercise.exercisebankingapp.exception.UserNotFoundException;
import com.exercise.exercisebankingapp.mapper.UserMapper;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.mapstruct.control.MappingControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User getUserByName(String name) {
        return userRepository.findUserByName(name).orElseThrow(() -> new UserNotFoundException("User not found with name: " + name));
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public List<Account> getUserAccounts(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            return user.getAccounts();
        }
        return null;
    }

    public void addUser(User user) {
        User newUser = userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        UserMapper.INSTANCE.updateUserFromDTO(userUpdateDTO, existingUser);
        return userRepository.save(existingUser);
    }

    public void deleteUserById(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
            userRepository.deleteById(userId);
    }

    public User deleteUserByName(String userName) {
        User user = userRepository.findUserByName(userName).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }

    public User deleteUserByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }
}
