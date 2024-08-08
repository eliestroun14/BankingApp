package com.exercise.exercisebankingapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${SECRET_KEY}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}