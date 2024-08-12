package com.exercise.exercisebankingapp.dataTransferObject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
public class AuthenticationResponse {

        private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

}
