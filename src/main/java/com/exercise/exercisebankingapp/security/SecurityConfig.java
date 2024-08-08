package com.exercise.exercisebankingapp.security;

import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.security.jwt.JwtFilter;
import com.exercise.exercisebankingapp.service.MyUserDetailsService;
import com.exercise.exercisebankingapp.security.jwt.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter, MyUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home", "/api/user/register", "/api/auth/login").permitAll();
                    registry.requestMatchers("/api/user/all", "/api/user/{userId}", "/api/user/accounts/{userId}", "/api/user/{userId}/status").hasRole(MyUser.Role.ADMIN.name());
                    registry.requestMatchers("/api/account/**").hasRole(MyUser.Role.ADMIN.name());
                    registry.requestMatchers("/user/home").hasAnyRole(MyUser.Role.USER.name(), MyUser.Role.ADMIN.name());
                    registry.requestMatchers("/admin/**").hasRole(MyUser.Role.ADMIN.name());
                    //registry.requestMatchers("/api/user/my-accounts", "/api/user/my-balance", "/api/user/my-infos").hasAnyRole(MyUser.Role.USER.name(), MyUser.Role.ADMIN.name());
                    registry.requestMatchers("/user/my-accounts", "/user/my-balance", "/user/my-infos").authenticated();
                    registry.anyRequest().authenticated();
                }).formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .permitAll()
                                .successHandler(new AuthenticationSuccessHandler())
                )/*
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .permitAll()
                                .successHandler(new AuthenticationSuccessHandler())
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
