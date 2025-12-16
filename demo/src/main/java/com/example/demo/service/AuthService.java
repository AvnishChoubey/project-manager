package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.response.JwtResponse;

@Service
public class AuthService {
    @Autowired JwtService jwtService;
    @Autowired AuthenticationManager authenticationManager;

    public JwtResponse loginUser(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return JwtResponse.builder().token(jwtService.generateToken(userDetails)).build();
        } catch (Exception e) {
            // Handle invalid credentials
            throw new RuntimeException("Invalid Credentials");
        }
    }
}