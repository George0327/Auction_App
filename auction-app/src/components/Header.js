import React from "react";
import { useNavigate } from "react-router-dom";
import "./elements.css";

const Header = () => {
  const navigate = useNavigate();

  const goToProfile = () => {
    navigate("/profile");
  };

  const goToAuction = () => {
    navigate("/auctions");
  };

  const goToHome = () => {
    navigate("/home");
  }

  return (
    <div className="top-bar">
      <h2 className="logo">All around auction</h2>
      <button className="btn btn-home" onClick={goToHome}>
        Home
      </button>
      <button className="btn btn-profile" onClick={goToProfile}>
        Profile
      </button>
      <button className="btn btn-auction" onClick={goToAuction}>
        Auctions
      </button>
    </div>
  );
};

export default Header;