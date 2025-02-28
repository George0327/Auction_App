package com.example.app.controller;

import com.example.app.model.Auction;
import com.example.app.model.AuctionCategory;
import com.example.app.model.UserInteraction;
import com.example.app.service.AuctionService;
import com.example.app.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserInteractionController {

    @Autowired
    UserInteractionService interactionService;

    @Autowired
    AuctionService auctionService;

    @PostMapping("/interactions/buttonClick")
    public ResponseEntity<Void> recordButtonClick(
            @RequestParam int userId,
            @RequestParam AuctionCategory category
    ) {
        interactionService.recordButtonClick(userId, category);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/interactions/filterUse")
    public ResponseEntity<Void> recordFilterUse(
            @RequestParam int userId,
            @RequestParam AuctionCategory category
    ) {
        interactionService.recordFilterUse(userId, category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mostAccessed/{userId}")
    public ResponseEntity<List<Auction>> getAuctionsFromMostAccessedCategory(@PathVariable int userId) {
        AuctionCategory mostAccessedCategory = interactionService.mostClickedCategory(userId);
        if (mostAccessedCategory == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Auction> auctions = auctionService.getAuctionsByCategory(mostAccessedCategory);
        return ResponseEntity.ok(auctions);
    }
}
