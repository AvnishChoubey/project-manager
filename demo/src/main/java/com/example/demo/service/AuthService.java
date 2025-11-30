package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class AuthService {
    @Autowired UserRepository authRepository;

    // public UserResponse loginUser(String email, String password) {
    //     return new UserResponse();
    //     Optional<User> optionalUser = authRepository.findByEmail();
    //     if(optionalUser.isEmpty()) {
    //         throw new Exception("Invalid email or password");
    //     }
    // }
}
