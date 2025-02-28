package com.example.app.service;

import com.example.app.model.Auction;
import com.example.app.model.Bid;
import com.example.app.model.User;
import com.example.app.repository.AuctionRepository;
import com.example.app.repository.BidRepository;
import com.example.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidService {

    private static final Logger logger = LoggerFactory.getLogger(BidService.class);

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionService auctionService;

    public void placeBid(int auctionId, int bidAmount, int userId) {
        logger.debug("Placing bid for auctionId {} with amount {} by userId {}", auctionId, bidAmount, userId);
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getCurrentBid() >= bidAmount) {
            throw new RuntimeException("Bid must be higher than the current bid.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setBidAmount(bidAmount);
        bid.setBidTime(LocalDateTime.now());
        bid.setUser(user); // Set the user

        bidRepository.save(bid);

        auction.setCurrentBid(bidAmount);
        auctionService.updateAuction(auction);
    }

    public List<Bid> getBidsForAuction(int auctionId) {
        return bidRepository.findByAuctionId(auctionId);
    }

    public List<Bid> getBidsByUser(int userId) {
        return bidRepository.findByUserId(userId);
    }
}