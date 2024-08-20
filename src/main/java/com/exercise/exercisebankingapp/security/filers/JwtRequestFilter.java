package com.exercise.exercisebankingapp.security.filers;

import com.exercise.exercisebankingapp.security.util.JwtUtil;
import com.exercise.exercisebankingapp.service.MyUserDetailsService;
import jakarta.persistence.Id;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = servletRequest.getHeader(HEADER_STRING);

        String username = null;
        String Jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            Jwt = authorizationHeader.substring(TOKEN_PREFIX.length());
            try {
                username = jwtUtil.extractUsername(Jwt);
                System.out.println("JWT Username: " + username); // debug
            } catch (Exception e) {
                System.out.println("Error extracting username: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(Jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("User Authorities: " + userDetails.getAuthorities()); // debug
            } else {
                System.out.println("Invalid JWT token"); // debug
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
