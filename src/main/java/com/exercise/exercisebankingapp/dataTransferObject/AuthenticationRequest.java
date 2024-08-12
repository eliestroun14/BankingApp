package com.exercise.exercisebankingapp.dataTransferObject;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationRequest {
    private String name;
    private String password;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

