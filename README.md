# All Around Auction

## Overview
All Around Auction is a full-stack web application for online auctions, where users can browse auctions, place bids, and receive personalized recommendations. The application is built with a **Spring Boot** backend and a **React** frontend, with **PostgreSQL** as the database.

## Features
- **User Authentication** (JWT-based login & registration)
- **Browse Auctions** with filtering by category
- **Bid on Auctions** with real-time bid validation
- **Personalized Recommendations** based on user interactions
- **Secure REST API** for all auction-related operations

## Tech Stack
- **Backend:** Spring Boot, Java, Spring Security, JPA, PostgreSQL
- **Frontend:** React, JavaScript
- **Authentication:** JSON Web Tokens (JWT)

## Project Structure
```
Auction_App (Backend)
│── src/main/java/com/example/app
│   ├── controller/ (API endpoints)
│   ├── model/ (Entities & data models)
│   ├── service/ (Business logic)
│   ├── repository/ (Database access)
│   ├── util/ (JWT utilities)
│   ├── config/ (CORS & Security Config)
│── src/main/resources
│   ├── application.properties (Database config)
│── pom.xml (Dependencies)
│
auction-app (Frontend)
│── src/
│   ├── components/ (React components)
│   ├── pages/ (Login, Signup, Auctions, Profile, Home)
│   ├── App.js (Routing)
│── package.json (Dependencies)
```

## Setup Instructions
### Backend (Spring Boot)
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/auction-app.git
   cd auction-app/Auction_App
   ```
2. Configure **PostgreSQL** database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/auction_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Build and run the backend:
   ```sh
   mvn spring-boot:run
   ```

### Frontend (React)
1. Navigate to the frontend directory:
   ```sh
   cd auction-app/auction-app
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the React app:
   ```sh
   npm start
   ```

## API Endpoints
### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/login` - Authenticate and get a JWT token

### Auctions
- `GET /api/auctions` - Get all auctions
- `GET /api/auctions/{id}` - Get a specific auction
- `POST /api/auctions` - Create a new auction
- `PUT /api/auctions/update` - Update an auction
- `DELETE /api/auctions/{id}` - Delete an auction

### Bidding
- `POST /api/bids/placeBid` - Place a bid
- `GET /api/bids/auction/{auctionId}` - Get bids for a specific auction

## License
This project is licensed under the **MIT License**.

## Contributors
- **Marinescu George**

## Contact
For any issues or questions, please open an issue in this repository or contact **george.marinescu2703@gmail.com**.

