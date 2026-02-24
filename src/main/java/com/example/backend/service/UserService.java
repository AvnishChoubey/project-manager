package com.example.backend.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.request.UserRequest;
import com.example.backend.response.UserResponse;
import com.example.backend.transformer.ModelToResponse;
import com.example.backend.transformer.RequestToModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = new ArrayList<>();
        for(int i=0;i<users.size();i++) {
            userResponses.add(ModelToResponse.userToUserResponse(users.get(i)));
        }
        
        return ResponseEntity.ok(userResponses);
    }

    public ResponseEntity<UserResponse> getUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        
        return ResponseEntity.ok(ModelToResponse.userToUserResponse(user));
    }

    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        
        log.info("CREATING USER...");

        User user = RequestToModel.userRequestToUser(userRequest);

        Optional<User> presentUser = userRepository.findByEmail(userRequest.getEmail());

        if(!presentUser.isEmpty()) {
            log.error("USER ALREADY PRESENT");
            throw new BadRequestException("User already present. Try with different email");
        }

        log.info("CREATING USER IN THE DATABASE");

        User savedUser = userRepository.save(user);

        URI location = URI.create("/user/" + savedUser.getId());
        
        return ResponseEntity.created(location).body(ModelToResponse.userToUserResponse(savedUser));
    }

    public ResponseEntity<UserResponse> updateUser(Long userId, @RequestBody UserRequest userRequest) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        
        if (userRequest.getRole() != null)
        user.setRole(userRequest.getRole());

        if(userRequest.isActive() == false)
        user.setActive(false);
        
        User updatedUser = userRepository.save(user);
        
        return ResponseEntity.ok(ModelToResponse.userToUserResponse(updatedUser));
    }
}
