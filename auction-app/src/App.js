import React from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import SignUp from "./components/SignUp";
import Login from "./components/Login";
import Home from "./components/Home";
import Profile from "./components/Profile";
import Auctions from "./components/Auctions";

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Redirect to /login when the root URL is accessed */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/auctions" element={<Auctions />} />
      </Routes>
    </Router>
  );
};

export default App;
