package com.exercise.exercisebankingapp.service;

import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findUserByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        var userObj = user.get();
        return User.builder()
                .username(userObj.getName())
                .password(userObj.getPassword())
                .roles(getRoles(userObj))
                .build();
    }

    private String[] getRoles(MyUser user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }
        return new String[]{user.getRole().name()};
    }


}
