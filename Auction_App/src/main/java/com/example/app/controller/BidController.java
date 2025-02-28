package com.example.app.controller;

import com.example.app.service.BidService;
import com.example.app.model.Bid;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @Transactional
    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<Bid>> getBidsForAuction(@PathVariable int auctionId) {
        List<Bid> bids = bidService.getBidsForAuction(auctionId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bid>> getBidsByUser(@PathVariable int userId) {
        List<Bid> bids = bidService.getBidsByUser(userId);
        return ResponseEntity.ok(bids);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<String> placeBid(@RequestParam int auctionId, @RequestParam int bidAmount, @RequestParam int userId) {
        try {
            bidService.placeBid(auctionId, bidAmount, userId);
            return ResponseEntity.ok("Bid placed successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}