// package com.example.demo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.service.AuthService;

// @RestController("/api/v1/auth")
// public class AuthController {
//     @Autowired AuthService authService;

//     @PostMapping("/login")
//     public UserResponse loginUser(@RequestBody UserRequest userRequest) {
//         return authService.loginUser(userRequest.getEmail(), userRequest.getPassword());
//     }
// }