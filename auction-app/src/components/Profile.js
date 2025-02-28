import React, { useState, useEffect } from "react";
import Header from "./Header";
import "./elements.css";

const Profile = () => {
  const [formData, setFormData] = useState({ username: "", email: "" });
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const token = localStorage.getItem("authToken");
        if (!token) {
          throw new Error("No token found, please log in again");
        }

        const response = await fetch("http://localhost:8080/api/user/current-user", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch profile data");
        }

        const result = await response.json();
        setFormData({ username: result.username, email: result.email });
      } catch (error) {
        console.error("Error fetching profile data:", error);
        setMessage("Error fetching profile data");
      }
    };

    fetchProfileData();
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("authToken");
      if (!token) {
        throw new Error("No token found, please log in again");
      }

      const response = await fetch("http://localhost:8080/api/user/update-user", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error("Failed to update profile");
      }

      alert("Profile updated successfully!");
    } catch (error) {
      console.error("Error updating profile:", error);
      alert("Error updating profile");
    }
  };

  const handleLogout = async () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  };

  return (
    <div className="container">
      <Header />
      <div className="content">
          <h2>Profile</h2>
          {message && <p className="message">{message}</p>}
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="username"
              placeholder="Username"
              value={formData.username}
              onChange={handleChange}
              required
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
              required
            />
            <button type="submit" className="btn btn-update">Update Profile</button>
          </form>
          <button onClick={handleLogout} className="btn btn-logout">Logout</button>
      </div>
    </div>
  );
};

export default Profile;