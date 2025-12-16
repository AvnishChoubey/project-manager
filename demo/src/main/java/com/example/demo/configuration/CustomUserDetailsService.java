package com.example.demo.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired UserRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = authRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid email");
        }
        return new CustomUserDetails(optionalUser.get());
    }
}