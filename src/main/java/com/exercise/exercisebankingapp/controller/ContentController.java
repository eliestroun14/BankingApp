package com.exercise.exercisebankingapp.controller;

import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.service.AccountService;
import com.exercise.exercisebankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContentController {

    final AccountService accountService;
    final UserService userService;

    public ContentController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }


    @GetMapping("/home")
    public String handleHome() {
        return "home";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "home_user";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "home_admin";
    }

    @GetMapping("/user/my-accounts")
    public List<Account> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        return accountService.getUserAccountsByUserName(username);
    }

    @GetMapping("/user/my-balance")
    public double getMyBalance(Authentication authentication) {
        String username = authentication.getName();
        MyUser myUser = userService.getUserByName(username);
        return accountService.getUserTotalMoney(myUser.getId());
    }

    @GetMapping("/user/my-infos")
    public MyUser getMyInfos(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByName(username);
    }

//    @GetMapping("/login")
//    public String handleLogin() {
//        return "login";
//    }

}
