package com.exercise.exercisebankingapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class AppConfig {

    @Value("${SECRET_KEY}")
    private String secretKey;

}