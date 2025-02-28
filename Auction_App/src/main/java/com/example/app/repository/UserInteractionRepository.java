package com.example.app.repository;

import com.example.app.model.AuctionCategory;
import com.example.app.model.User;
import com.example.app.model.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Integer> {
    Optional<UserInteraction> findByUserAndCategory(User user, AuctionCategory category);

    List<UserInteraction> findAllByUser(User user);
}
