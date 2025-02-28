import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./elements.css";

const Login = () => {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || "Login failed!");
      }

      const result = await response.json();
      localStorage.setItem("authToken", result.token);
      localStorage.setItem("userId", result.userId);
      setMessage(`Welcome, ${result.username}!`);
      setTimeout(() => {
        navigate("/home");
      }, 1000);
    } catch (error) {
      setMessage(error.message);
    }
  };

  const goToSignUp = () => {
    navigate("/signup");
  };

  return (
    <div className="container">
      <div className="content">
        <h2>Login</h2>
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
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <button type="submit" className="btn btn-login">Login</button>
        </form>
        <p>
          Don't have an account?{" "}
          <button className="btn-link" onClick={goToSignUp}>
            Sign Up
          </button>
        </p>
      </div>
    </div>
  );
};

export default Login;