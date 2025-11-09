package com.example.lostfound.controller;

import com.example.lostfound.model.User;
import com.example.lostfound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
        }

        user.setRole("USER"); // force role as USER for every new registration
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Registered successfully as user"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        User user = userRepository.findByEmail(email);

        if (email.equals("admin@lostfound.com") && password.equals("admin123")) {
            return ResponseEntity.ok(Map.of("message", "Login successful", "role", "ADMIN"));
        }

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }


        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);


    }
}
