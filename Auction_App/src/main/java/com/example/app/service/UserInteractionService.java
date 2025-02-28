package com.example.app.service;

import com.example.app.model.Auction;
import com.example.app.model.AuctionCategory;
import com.example.app.model.User;
import com.example.app.model.UserInteraction;
import com.example.app.repository.UserInteractionRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInteractionService {
    @Autowired
    private UserInteractionRepository userInteractionRepository;

    @Autowired
    private UserRepository userRepository;

    public void recordButtonClick(int userId, AuctionCategory category) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserInteraction userInteraction = userInteractionRepository
                .findByUserAndCategory(user, category)
                .orElse(new UserInteraction(user, category));

        userInteraction.setButtonClicks(userInteraction.getButtonClicks() + 1);
        userInteractionRepository.save(userInteraction);
    }

    public void recordFilterUse(int userId, AuctionCategory category) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserInteraction userInteraction = userInteractionRepository
                .findByUserAndCategory(user, category)
                .orElse(new UserInteraction(user, category));

        userInteraction.setFilterUses(userInteraction.getFilterUses() + 1);
        userInteractionRepository.save(userInteraction);
    }

    public AuctionCategory mostClickedCategory(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<UserInteraction> userInteractions = userInteractionRepository.findAllByUser(user);

        return userInteractions.stream()
                .max((a, b) -> a.getButtonClicks() - b.getFilterUses())
                .map(UserInteraction::getCategory)
                .orElse(null);
    }
}
