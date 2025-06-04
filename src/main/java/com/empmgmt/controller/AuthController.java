package com.empmgmt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.empmgmt.model.Users;
import com.empmgmt.service.JWTService;
import com.empmgmt.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        userService.register(user.getName(), user.getEmailId(), user.getPassword(), user.getRole());  
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/unassigned")
    public ResponseEntity<List<Users>> getUnassignedEmployees() {
        return ResponseEntity.ok(userService.getUnassignedEmployees());
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users user) {
        System.out.println("Login API hit: " + user.getEmailId());

        Users authUser = userService.findByEmailId(user.getEmailId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), authUser.getPassword())) {
            System.out.println("Password mismatch! Login failed.");
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtService.getToken(authUser.getEmpId(), authUser.getRole().name(), authUser.getEmailId());
        System.out.println("Generated JWT Token: " + token);

        return ResponseEntity.ok(Map.of("token", token));
    }


}
