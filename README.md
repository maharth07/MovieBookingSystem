# Multiplex Movie Booking System

A console-based multiplex movie ticket booking system built in Java. This project simulates a real-world movie theater management system with tiered seating, snack bar, promo codes, and admin analytics.

## Features

### User Portal
- **Browse Movies** – View all currently showing movies with genres
- **View Schedules** – See all available showtimes for a selected movie across multiple screens
- **Seat Selection** – Interactive seat map with tiered pricing (VIP, Premium, Executive, Normal)
- **Snack Bar** – Add snacks to your booking (Popcorn, Nachos, Coke, Water)
- **Promo Codes** – Apply discount codes at checkout
- **Bulk Booking Reward** – Book 5+ seats and earn a one-time 20% discount code
- **Ticket Wallet** – View all your active bookings
- **Cancellation** – Cancel tickets with full refund

### Admin Portal
- **Revenue Analytics** – View detailed per-show and per-movie revenue breakdown
- **Add Movies** – Add new movies to the multiplex catalog
- **Schedule Shows** – Create new showtimes for existing movies
- **Remove Movies** – Delete movies along with associated shows and tickets

## Seat Layout

Each screen has 36 seats arranged in 6 rows:

| Row | Tier | Price (Rs.) |
|-----|------|-------------|
| A | VIP | 500.00 |
| B, C | Premium | 350.00 |
| D, E | Executive | 250.00 |
| F | Normal | 150.00 |

## Billing

- **GST**: 18% on subtotal after discount
- **Convenience Fee**: Rs. 35.00 per transaction
- **Promo Codes**: `WELCOME10` (10% off), `FESTIVAL20` (20% off)

## How to Run

### Prerequisites
- Java 17 or higher

### Compile and Run

```bash
javac MultiplexBookingSystem.java
java MultiplexBookingSystem
```

## Pre-loaded Data

The system comes with 8 movies and 26 pre-scheduled shows:

- **Trending**: Dune Part Two (6 shows), Oppenheimer (5 shows), Avatar (4 shows)
- **Popular**: John Wick 4 (3 shows), Spider-Verse (3 shows)
- **Classics**: The Dark Knight (2 shows), Inception (1 show), Interstellar (1 show)

## Project Structure

```
MultiplexBookingSystem/
├── MultiplexBookingSystem.java    # Main application (all classes in one file)
├── README.md                      # Project documentation
└── .gitignore                     # Git ignore rules
```

## Technologies Used

- Java 17+
- Java Collections Framework
- Java Streams API
- UUID for ticket/promo code generation

## Author

Built as a Java programming project to demonstrate OOP concepts, enum usage, collections, and stream operations.
