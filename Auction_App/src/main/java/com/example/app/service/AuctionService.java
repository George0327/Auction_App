package com.example.app.service;

import com.example.app.model.Auction;
import com.example.app.model.AuctionCategory;
import com.example.app.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Optional<Auction> getAuctionById(int id) {
        return auctionRepository.findById(id);
    }

    public Auction saveAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public Auction updateAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public void deleteAuctionById(int id) {
        auctionRepository.deleteById(id);
    }

    public List<Auction> getAuctionsByCategory(AuctionCategory category) {
        return auctionRepository.findByCategory(category);
    }
}