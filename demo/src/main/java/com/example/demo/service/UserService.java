package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

@Service
public class UserService {
    @Autowired UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(int i=0;i<users.size();i++) {
            userResponses.add(ModelToResponse.userToUserResponse(users.get(i)));
        }
        return userResponses;
    }

    public UserResponse getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        } else {
            User user = optionalUser.get();
            return ModelToResponse.userToUserResponse(user);
        }
    }

    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User user = RequestToModel.userRequestToUser(userRequest);
        User savedUser = userRepository.save(user);
        return ModelToResponse.userToUserResponse(savedUser);
    }

    public UserResponse updateUser(Long userId, @RequestBody UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        } else {
            User user = optionalUser.get();
            
            if (userRequest.getRole() != null)
            user.setRole(userRequest.getRole());

            if(userRequest.isActive() == false)
            user.setActive(false);
            
            User updatedUser = userRepository.save(user);
            return ModelToResponse.userToUserResponse(updatedUser);
        }
    }
}
