package com.example.app.service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String signUp(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new RuntimeException("Email already exists!");
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password!");
        }

        return "Login successful! User ID: " + user.get().getId();
    }

    public Optional<User> getUserProfile(String username) {
        return userRepository.findByUsername(username);
    }

    public String updateUserProfile(String username, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findByUsername(username);

        if (existingUserOpt.isEmpty()) {
            return "User not found";
        }

        User existingUser = existingUserOpt.get();

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        userRepository.save(existingUser);

        return "Profile updated successfully";
    }
}