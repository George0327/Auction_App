import React, { useEffect, useState } from "react";
import Header from "./Header";
import "./elements.css";

const Home = () => {
  const [auctions, setAuctions] = useState([]);
  const userId = localStorage.getItem("userId");

  // Fetch auctions from the most accessed category
  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/mostAccessed/${userId}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setAuctions(data);
      } catch (error) {
        console.error("Error fetching auctions:", error);
      }
    };

    fetchAuctions();
  }, [userId]);

  return (
    <div className="container">
      <Header />
      <div className="content" style={{ maxWidth: "600px" }}>
        <h2>Welcome to the Home Page!</h2>
        <p>Here are some auctions from your most clicked category:</p>
        {auctions.length > 0 ? (
          <div className="grid">
            {auctions.map((auction) => (
              <div key={auction.id} className="grid-item">
                <h3>{auction.name}</h3>
                <p>{auction.description}</p>
                <p>Starting price: ${auction.startingPrice}</p>
                <p>Current bid: ${auction.currentBid}</p>
              </div>
            ))}
          </div>
        ) : (
          <p>No auctions to display yet. Start exploring categories!</p>
        )}
      </div>
    </div>
  );
};

export default Home;
