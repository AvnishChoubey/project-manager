package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.backend.configuration.CustomUserDetails;
import com.example.backend.exception.InvalidCredentialsException;
import com.example.backend.response.JwtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;

    public JwtResponse loginUser(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

            CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();

            String token = jwtService.generateToken(details.getUserId(), details.getUsername());

            return JwtResponse.builder().token(token).build();
        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }
}