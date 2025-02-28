package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.UserService;
import com.example.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Map<String, String> signUp(@RequestBody User user) {
        String message = userService.signUp(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        try {
            String loginMessage = userService.login(username, password);

            String token = JwtUtil.generateToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("message", loginMessage);
            response.put("token", token);
            response.put("username", username);
            response.put("email", userService.getUserProfile(username).get().getEmail());
            int userId = userService.getUserProfile(username).get().getId();
            response.put("userId", String.valueOf(userId));
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid username or password!");
        }
    }

    @GetMapping("/logout")
    public Map<String, String> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return response; // No session to invalidate, token simply expires
    }
}