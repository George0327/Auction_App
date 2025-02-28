import React, { useEffect, useState } from "react";
import "./elements.css";
import Header from "./Header";

const Auctions = () => {
  const [auctions, setAuctions] = useState([]);
  const [isBidModalOpen, setIsBidModalOpen] = useState(false);
  const [isBidsListModalOpen, setIsBidsListModalOpen] = useState(false);
  const [selectedAuctionId, setSelectedAuctionId] = useState(null);
  const [bidAmount, setBidAmount] = useState("");
  const [bids, setBids] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  const userId = localStorage.getItem("userId");

    const fetchAuctions = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/auctions");
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        console.log(data);
        setAuctions(data);
      } catch (error) {
        console.error("Error fetching auctions:", error);
      }
    };

    useEffect(() => {
        fetchAuctions();
      }, []);


  const fetchBids = async (auctionId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/bids/auction/${auctionId}`);
      if (!response.ok) {
        throw new Error(`Error fetching bids for auction ${auctionId}`);
      }
      const data = await response.json();
      setBids(data);
    } catch (error) {
      console.error("Error fetching bids:", error);
    }
  };

  const fetchAuctionsByCategory = async (category) => {
    try {
      const response = await fetch(`http://localhost:8080/api/auctions/category/${category}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setAuctions(data);
    } catch (error) {
      console.error(`Error fetching auctions for category ${category}:`, error);
    }
  };

  const handleBidClick = async (auctionId, category) => {
    setSelectedAuctionId(auctionId);
    setIsBidModalOpen(true);
  
    try {
      await fetch(`http://localhost:8080/api/interactions/buttonClick?userId=${userId}&category=${category}`, {
        method: "POST",
      });
    } catch (error) {
      console.error("Error recording button click:", error);
    }
  };

  const handleSeeAllBidsClick = async (auctionId, category) => {
    setSelectedAuctionId(auctionId);
    setIsBidsListModalOpen(true);
    fetchBids(auctionId);

    try {
      await fetch(`http://localhost:8080/api/interactions/buttonClick?userId=${userId}&category=${category}`, {
        method: "POST",
      });
    } catch (error) {
      console.error("Error recording button click:", error);
    }
  };

  const handlePlaceBid = async () => {
    const auction = auctions.find((auction) => auction.id === selectedAuctionId);
  
    if (parseFloat(bidAmount) <= auction.currentBid) {
      alert("Your bid must be higher than the current bid!");
      return;
    }
  
    try {
      const response = await fetch(
        `http://localhost:8080/api/bids/placeBid?auctionId=${selectedAuctionId}&bidAmount=${bidAmount}&userId=${userId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          credentials: "include"
        }
      );
      if (!response.ok) {
        const errorMessage = await response.text();
        alert(errorMessage);
      } else {
        alert("Bid placed successfully!");
        setIsBidModalOpen(false);
        setBidAmount("");
      }
    } catch (error) {
      console.error("Error placing bid:", error);
      alert("An error occurred while placing the bid.");
    }
  };

  const closeBidModal = () => {
    setIsBidModalOpen(false);
    setBidAmount("");
  };

  const closeBidsListModal = () => {
    setIsBidsListModalOpen(false);
  };

  const handleCategoryChange = async (e) => {
    const selectedCategory = e.target.value;
    setSelectedCategory(selectedCategory);
  
    if (selectedCategory === "") {
      fetchAuctions();
    } else {
      try {
        await fetch(`http://localhost:8080/api/interactions/filterUse?userId=${userId}&category=${selectedCategory}`, {
          method: "POST",
        });
        fetchAuctionsByCategory(selectedCategory);
      } catch (error) {
        console.error("Error recording filter use:", error);
      }
    }
  };

  return (
    <div className="container">
      <Header />
      <div className="content" style={{ maxWidth: "1200px" }}>
        <h2>All Auctions</h2>

         <div className="filter">
          <label htmlFor="category-select">Filter by Category: </label>
          <select
            id="category-select"
            value={selectedCategory}
            onChange={handleCategoryChange}
          >
            <option value="">All Categories</option>
            <option value="TSHIRT">T-Shirts</option>
            <option value="HOODIE">Hoodies</option>
            <option value="MUG">Mugs</option>
            <option value="TOTEBAG">Totebags</option>
            <option value="STICKER">Stickers</option>
            <option value="POSTER">Posters</option>
            <option value="OTHER">Other</option>
          </select>
        </div>

        <div className="grid">
          {auctions.map((auction) => (
            <div key={auction.id} className="grid-item">
              <h3>{auction.name}</h3>
              <p>{auction.description}</p>
              <p>Starting price: ${auction.startingPrice}</p>
              <price>Current bid: ${auction.currentBid}</price>
              <p>Category: {auction.category}</p>
              <button className="btn" onClick={() => handleBidClick(auction.id, auction.category)}>
                Place a Bid
              </button>

              <button
                className="btn"
                onClick={() => handleSeeAllBidsClick(auction.id, auction.category)}
              >
                See All Bids
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* Bid Modal */}
      {isBidModalOpen && (
        <div className="modal">
          <div className="modal-content">
            <span className="close-btn" onClick={closeBidModal}>
              &times;
            </span>
            <h3>Place a Bid</h3>
            <input
              type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              placeholder="Enter bid amount"
            />
            <button onClick={handlePlaceBid}>Place Bid</button>
          </div>
        </div>
      )}

      {/* Bids List Modal */}
      {isBidsListModalOpen && (
        <div className="modal">
          <div className="modal-content">
            <h3>All Bids for Auction ID: {selectedAuctionId}</h3>
            <ul>
              {bids.length > 0 ? (
                bids.map((bid, index) => (
                  <li key={index}>
                    Bid Amount: ${bid.bidAmount} - Bidder: {bid.user.username}
                  </li>
                ))
              ) : (
                <p>No bids placed yet.</p>
              )}
            </ul>
            <span className="close-btn" onClick={closeBidsListModal}>
              &times;
            </span>
          </div>
        </div>
      )}
    </div>
  );
};

export default Auctions;
