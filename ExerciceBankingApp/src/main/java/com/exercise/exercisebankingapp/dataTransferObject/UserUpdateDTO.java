package com.exercise.exercisebankingapp.dataTransferObject;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDTO {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public UserUpdateDTO(String name, String email, LocalDate dateOfBirth, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "userName='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userAddress='" + address + '\'' +
                ", userPhoneNumber='" + phoneNumber + '\'' +
                ", userDateOfBirth=" + dateOfBirth +
                '}';
    }
}
