package com.exercise.exercisebankingapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_table")
@Getter
@Setter
//@EntityListeners(UserEntityListener.class)
public class MyUser {;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Status status;
    private String password;
    private Role role;

    @Transient
    private Integer age;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    /* /public String getRoles() {
        return role;
    }*/


    public enum Status {
        ONBOARDING,
        ACTIVE,
        CLOSED
    }

    public enum Role {
        USER,
        ADMIN
    }

    @OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public MyUser() {
    }

    public MyUser(MyUser newUser) {
        System.out.println("constructor from user");
        this.id = newUser.getId();
        this.name = newUser.getName();
        this.email = newUser.getEmail();
        this.dateOfBirth = newUser.getDateOfBirth();
        this.address = newUser.getAddress();
        this.role = newUser.getRole();
        this.phoneNumber = newUser.getPhoneNumber();
        this.password = newUser.getPassword();
        this.status = Status.ONBOARDING;
        this.age = getAge();
        createInitialAccount();
    }

    public MyUser(String name, String email, LocalDate dateOfBirth, String address, String phoneNumber, MyUser.Role role, String password, MyUser.Status status) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
        this.age = getAge();
        createInitialAccount();
    }

    public MyUser(String name, String email, LocalDate dateOfBirth, String address, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = Role.USER;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = Status.ONBOARDING;
        this.age = getAge();
        createInitialAccount();
    }

    public void createInitialAccount() {
        System.out.println("createInitialAccount");
        Account account = new Account();
        account.setMyUser(this);
        account.setBalance(100.0);
        //accountService.addAccount(account);
        this.accounts.add(account);
    }

    public Integer getAge() {
        if (dateOfBirth == null) {
            return null;
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", status=" + status +
                '}';
    }
}
