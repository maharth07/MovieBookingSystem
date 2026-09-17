# Problem Statement

## Multiplex Movie Booking System

### Objective

Design and develop a **console-based Multiplex Movie Ticket Booking System** in Java that simulates the real-world operations of a movie theater. The system should support two distinct user roles — **User** and **Admin** — each with their own portal and set of functionalities.

---

### Problem Description

A multiplex cinema chain requires a software system to manage movie screenings, seat reservations, and ticket sales across multiple screens. The system must handle the following core workflows:

1. **Movie & Schedule Management** – Maintain a catalog of movies with genres, and schedule showtimes across multiple screens.
2. **Seat Reservation** – Allow customers to browse available seats (organized by tier/pricing), select seats, and complete bookings.
3. **Billing & Payments** – Calculate ticket prices based on seat tier, apply taxes (GST), convenience fees, and optional promotional discounts.
4. **Ticket Management** – Issue digital tickets, maintain a user wallet of active bookings, and support cancellations with refunds.
5. **Admin Analytics** – Provide revenue reports and operational insights to administrators.

---

### Functional Requirements

#### User Portal

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Browse Movies** | View all currently showing movies with their genres |
| 2 | **View Schedules** | See all available showtimes for a selected movie across multiple screens |
| 3 | **Seat Selection** | Interactive seat map display with tiered pricing (VIP, Premium, Executive, Normal) |
| 4 | **Snack Bar** | Add-on snack items (Popcorn, Nachos, Coke, Water) during booking |
| 5 | **Promo Codes** | Apply permanent or single-use discount codes at checkout |
| 6 | **Bulk Booking Reward** | Automatically generate a one-time 20% discount code when 5+ seats are booked |
| 7 | **Ticket Wallet** | View all active/confirmed bookings |
| 8 | **Ticket Cancellation** | Cancel a booking by Ticket ID with full refund |

#### Admin Portal

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Revenue Analytics** | View detailed per-show and per-movie revenue breakdown with seat occupancy |
| 2 | **Add Movie** | Add new movies to the multiplex catalog with title and genre |
| 3 | **Schedule Show** | Create new showtimes for existing movies on specified screens |
| 4 | **Remove Movie** | Delete a movie along with all associated shows and tickets |

---

### Seat Layout & Pricing

Each screen consists of **36 seats** arranged in **6 rows** (A–F) with **6 seats per row**, categorized into four pricing tiers:

| Row(s) | Tier | Price (Rs.) |
|--------|------|-------------|
| A | VIP | 500.00 |
| B, C | Premium | 350.00 |
| D, E | Executive | 250.00 |
| F | Normal | 150.00 |

- Booked seats are displayed as `[ XX ]` on the seat map.
- Available seats show their seat ID (e.g., `[  B3]`).

---

### Billing Rules

| Component | Details |
|-----------|---------|
| **Seat Subtotal** | Sum of selected seat prices based on tier |
| **Snack Subtotal** | Sum of selected snack item prices |
| **Promo Discount** | Applied on (Seat Subtotal + Snack Subtotal) before tax |
| **GST** | 18% on the amount after discount |
| **Convenience Fee** | Flat Rs. 35.00 per transaction |
| **Grand Total** | (Subtotal − Discount) + GST + Convenience Fee |

#### Available Promo Codes

| Code | Discount | Type |
|------|----------|------|
| `WELCOME10` | 10% off | Permanent (one-time use per session) |
| `FESTIVAL20` | 20% off | Permanent (one-time use per session) |
| `BULK20-XXXX` | 20% off | Auto-generated single-use reward for bulk bookings (5+ seats) |

---

### Snack Bar Menu

| Item | Description | Price (Rs.) |
|------|-------------|-------------|
| Popcorn | Large Caramel Popcorn | 220.00 |
| Nachos | Jalapeno Cheese Nachos | 180.00 |
| Coke | Fountain Coke | 120.00 |
| Water | Mineral Water | 60.00 |

---

### Technical Requirements

| Requirement | Specification |
|-------------|---------------|
| **Language** | Java 17 or higher |
| **Architecture** | Single-file application (all classes in one file) |
| **Data Storage** | In-memory (no database or file I/O required) |
| **Interface** | Console-based (text UI via `Scanner`) |
| **Pre-loaded Data** | 8 movies and 26 scheduled shows across 8 screens |

### Key Java Concepts Demonstrated

- **Enums** – `SeatTier` and `SnackItem` with parameterized constructors
- **OOP Principles** – Encapsulation, composition (Movie → Show → Seat → Ticket)
- **Collections Framework** – `ArrayList`, `HashMap`, `HashSet` for data management
- **Streams API** – Filtering, mapping, grouping, and collecting data
- **UUID Generation** – Unique ticket IDs and promo codes
- **Method Overriding** – `equals()` and `hashCode()` in entity classes

---

### Constraints

1. All data is stored in-memory; no persistence between sessions.
2. No authentication is required — user and admin roles are selected from the main menu.
3. Seat availability is shared across all users within a single session.
4. Promo codes (excluding permanent ones) can only be used once.
5. Cancellation provides a full refund and revokes any bulk reward code earned from that booking.

---

### Sample Workflow

```
1. User selects "Login as User"
2. User browses movies → selects "Dune: Part Two"
3. Views available shows → picks "SH101 – Screen 1 – 09:00 AM"
4. Sees the seat map → selects seats B1, B2, B3
5. Adds Popcorn and Coke from the snack bar
6. Applies promo code "WELCOME10"
7. System calculates billing:
   - Seats: 3 × Rs. 350 = Rs. 1050.00
   - Snacks: Rs. 220 + Rs. 120 = Rs. 340.00
   - Subtotal: Rs. 1390.00
   - Discount (10%): -Rs. 139.00
   - After Discount: Rs. 1251.00
   - GST (18%): Rs. 225.18
   - Convenience Fee: Rs. 35.00
   - Grand Total: Rs. 1511.18
8. Ticket is confirmed and added to wallet
```

---

### Deliverables

1. `MultiplexBookingSystem.java` – Complete source code
2. `README.md` – Project documentation with setup instructions
3. `statement.md` – Problem statement (this document)
