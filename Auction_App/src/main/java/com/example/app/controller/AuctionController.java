package com.example.app.controller;

import com.example.app.model.Auction;
import com.example.app.model.AuctionCategory;
import com.example.app.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable int id) {
        return auctionService.getAuctionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        return ResponseEntity.ok(auctionService.saveAuction(auction));
    }

    @PutMapping("/update")
    public ResponseEntity<Auction> updateAuction(@RequestBody Auction auction) {
        return ResponseEntity.ok(auctionService.updateAuction(auction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable int id) {
        auctionService.deleteAuctionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Auction>> getAuctionsByCategory(@PathVariable AuctionCategory category) {
        return ResponseEntity.ok(auctionService.getAuctionsByCategory(category));
    }
}