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
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> optionalUser = authRepository.findByName(name);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username");
        }
        return new CustomUserDetails(optionalUser.get());
    }
}