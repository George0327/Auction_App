package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.UserService;
import com.example.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current-user")
    public Map<String, String> getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = JwtUtil.extractUsername(token);

            if (username != null && JwtUtil.validateToken(token, username)) {
                User user = userService.getUserProfile(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                Map<String, String> response = new HashMap<>();
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                return response;
            }
        }
        throw new RuntimeException("No user is logged in or token is invalid");
    }

    @PutMapping("/update-user")
    public Map<String, String> updateUserProfile(@RequestBody User updatedUser, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = JwtUtil.extractUsername(token);

            if (username != null && JwtUtil.validateToken(token, username)) {
                String message = userService.updateUserProfile(username, updatedUser);
                Map<String, String> response = new HashMap<>();
                response.put("message", message);
                return response;
            }
        }
        throw new RuntimeException("No user is logged in or token is invalid");
    }
}