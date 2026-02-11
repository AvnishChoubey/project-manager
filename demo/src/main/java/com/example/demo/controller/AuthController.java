package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.LoginRequest;
import com.example.demo.response.JwtResponse;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired AuthService authService;

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }
}