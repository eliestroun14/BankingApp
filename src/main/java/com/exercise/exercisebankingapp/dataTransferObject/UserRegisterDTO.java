package com.exercise.exercisebankingapp.dataTransferObject;

import com.exercise.exercisebankingapp.entity.MyUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterDTO {

    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String password;
    private MyUser.Status status;
    private MyUser.Role role;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "userName='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userDateOfBirth=" + dateOfBirth +
                ", userAddress='" + address + '\'' +
                ", userPhoneNumber='" + phoneNumber + '\'' +
                ", userPassword='" + password + '\'' +
                ", userStatus=" + status +
                ", userRole=" + role +
                '}';
    }
}
