package com.example.app.repository;

import com.example.app.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findByAuctionId(int auctionId);
    List<Bid> findByUserId(int userId);
}
