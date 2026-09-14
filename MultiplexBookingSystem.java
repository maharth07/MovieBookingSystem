import java.util.*;
import java.util.stream.Collectors;

// ======= ENUMS =======

enum SeatTier {
    VIP(500.0), PREMIUM(350.0), EXECUTIVE(250.0), NORMAL(150.0);
    private final double price;
    SeatTier(double price) { this.price = price; }
    public double getPrice() { return price; }
}

enum SnackItem {
    POPCORN("Large Caramel Popcorn", 220.0),
    NACHOS("Jalapeno Cheese Nachos", 180.0),
    COKE("Fountain Coke", 120.0),
    WATER("Mineral Water", 60.0);

    private final String desc;
    private final double price;

    SnackItem(String desc, double price) {
        this.desc = desc;
        this.price = price;
    }
    public String getDescription() { return desc; }
    public double getPrice() { return price; }
}

// ======= ENTITY CLASSES =======

class Seat {
    private final String seatId;
    private final SeatTier tier;
    private boolean isBooked;

    public Seat(String seatId, SeatTier tier) {
        this.seatId = seatId;
        this.tier = tier;
        this.isBooked = false;
    }

    public String getSeatId() { return seatId; }
    public SeatTier getTier() { return tier; }
    public boolean isBooked() { return isBooked; }
    public void book() { this.isBooked = true; }
    public void unbook() { this.isBooked = false; }
}

class Movie {
    private final String movieId;
    private final String title;
    private final String genre;

    public Movie(String movieId, String title, String genre) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
    }

    public String getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}

class Show {
    private final String showId;
    private final Movie movie;
    private final String screenName;
    private final String time;
    private final List<Seat> seats;
    private double totalRevenue;

    public Show(String showId, Movie movie, String screenName, String time) {
        this.showId = showId;
        this.movie = movie;
        this.screenName = screenName;
        this.time = time;
        this.seats = initializeSeats();
        this.totalRevenue = 0.0;
    }

    private List<Seat> initializeSeats() {
        List<Seat> seatList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) seatList.add(new Seat("A" + i, SeatTier.VIP));
        for (char row = 'B'; row <= 'C'; row++) {
            for (int i = 1; i <= 6; i++) seatList.add(new Seat(row + String.valueOf(i), SeatTier.PREMIUM));
        }
        for (char row = 'D'; row <= 'E'; row++) {
            for (int i = 1; i <= 6; i++) seatList.add(new Seat(row + String.valueOf(i), SeatTier.EXECUTIVE));
        }
        for (int i = 1; i <= 6; i++) seatList.add(new Seat("F" + i, SeatTier.NORMAL));
        return seatList;
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public String getScreenName() { return screenName; }
    public String getTime() { return time; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalRevenue() { return totalRevenue; }
    public void addRevenue(double amt) { this.totalRevenue += amt; }
    public void deductRevenue(double amt) { this.totalRevenue -= amt; }

    public void displaySeatMap() {
        System.out.println("\n============== [ " + screenName.toUpperCase() + " ] ==============");
        SeatTier currentTier = null;
        for (int i = 0; i < seats.size(); i++) {
            Seat s = seats.get(i);
            if (currentTier != s.getTier()) {
                currentTier = s.getTier();
                System.out.println("\n--- " + currentTier.name() + " CLASS (Rs. " + currentTier.getPrice() + ") ---");
            }
            if (s.isBooked()) {
                System.out.print("[ XX ] ");
            } else {
                System.out.printf("[%4s] ", s.getSeatId());
            }
            if ((i + 1) % 6 == 0) System.out.println();
        }
        System.out.println("==========================================\n");
    }
}

class Ticket {
    private final String ticketId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final List<SnackItem> snacks;

    private final double seatTotal;
    private final double snackTotal;
    private final double discount;
    private final double gst;
    private final double convFee;
    private final double grandTotal;
    private final String promoEarned;

    public Ticket(Show show, List<Seat> bookedSeats, List<SnackItem> snacks,
                  double seatTotal, double snackTotal, double discount,
                  double gst, double convFee, double grandTotal, String promoEarned) {
        this.ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.snacks = snacks;
        this.seatTotal = seatTotal;
        this.snackTotal = snackTotal;
        this.discount = discount;
        this.gst = gst;
        this.convFee = convFee;
        this.grandTotal = grandTotal;
        this.promoEarned = promoEarned;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<Seat> getBookedSeats() { return bookedSeats; }
    public double getGrandTotal() { return grandTotal; }
    public String getPromoEarned() { return promoEarned; }

    public void printTicket() {
        System.out.println("\n+--------------------------------------------------+");
        System.out.println("|                 CONFIRMED TICKET                 |");
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Ticket ID : %-36s |\n", ticketId);
        System.out.printf("| Movie     : %-36s |\n", show.getMovie().getTitle());
        System.out.printf("| Screen    : %-15s | Time: %-12s |\n", show.getScreenName(), show.getTime());

        String seatNums = bookedSeats.stream().map(Seat::getSeatId).collect(Collectors.joining(", "));
        if (seatNums.length() > 36) seatNums = seatNums.substring(0, 33) + "...";
        System.out.printf("| Seats     : %-36s |\n", seatNums);

        if (!snacks.isEmpty()) {
            String snackStr = snacks.stream().map(SnackItem::name).collect(Collectors.joining(", "));
            if (snackStr.length() > 36) snackStr = snackStr.substring(0, 33) + "...";
            System.out.printf("| Snacks    : %-36s |\n", snackStr);
        }

        System.out.println("|--------------------------------------------------|");
        System.out.printf("| Seats Subtotal             : Rs. %10.2f    |\n", seatTotal);
        System.out.printf("| Snacks Subtotal            : Rs. %10.2f    |\n", snackTotal);

        if (discount > 0) {
            System.out.printf("| Discount Applied           :-Rs. %10.2f    |\n", discount);
        }

        System.out.println("|                                                  |");
        System.out.printf("| GST (18%%)                  : Rs. %10.2f    |\n", gst);
        System.out.printf("| Convenience Fee            : Rs. %10.2f    |\n", convFee);
        System.out.println("|--------------------------------------------------|");
        System.out.printf("| GRAND TOTAL                : Rs. %10.2f    |\n", grandTotal);

        if (promoEarned != null) {
            System.out.println("|==================================================|");
            System.out.println("| BULK BOOKING REWARD UNLOCKED!                    |");
            System.out.println("| You booked 5+ tickets! Enjoy 20% OFF next time!  |");
            System.out.printf("| Your One-Time Code: %-28s |\n", promoEarned);
        }
        System.out.println("+--------------------------------------------------+\n");
    }
}

// main class - handles everything
public class MultiplexBookingSystem {

    private static final List<Movie> movies = new ArrayList<>();
    private static final List<Show> shows = new ArrayList<>();
    private static final List<Ticket> userWallet = new ArrayList<>();

    private static final Map<String, Double> permanentPromos = Map.of("FESTIVAL20", 0.20, "WELCOME10", 0.10);
    private static final Map<String, Double> singleUsePromos = new HashMap<>();
    private static final Set<String> usedPromos = new HashSet<>();

    private static final Scanner sc = new Scanner(System.in);
    private static int idCounter = 100;

    public static void main(String[] args) {
        loadSeedData();
        mainMenu();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("       MULTIPLEX MOVIE BOOKING SYSTEM       ");
            System.out.println("============================================");
            System.out.println("1. Login as User");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit Application");
            System.out.print("Select Portal: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> userMenu();
                case "2" -> adminMenu();
                case "0" -> {
                    System.out.println("Shutting down... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid selection. Try again.");
            }
        }
    }

    // ---- user side ----

    private static void userMenu() {
        while (true) {
            System.out.println("\n------------- USER DASHBOARD -------------");
            System.out.println("1. Book Tickets (Browse Movies & Schedules)");
            System.out.println("2. View My Tickets (Wallet)");
            System.out.println("3. Cancel a Ticket");
            System.out.println("0. Back to Main Menu / Logout");
            System.out.print("Select Action: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> bookTickets();
                case "2" -> showWallet();
                case "3" -> cancelTicket();
                case "0" -> { return; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void bookTickets() {
        if (movies.isEmpty()) {
            System.out.println("\nNo movies currently playing.");
            return;
        }

        System.out.println("\n--- Now Showing in Theaters ---");
        movies.forEach(m -> System.out.printf("[%s] %s (%s)\n", m.getMovieId(), m.getTitle(), m.getGenre()));

        System.out.print("\nEnter Movie ID to view schedules (or 0 to back): ");
        String movieId = sc.nextLine().trim().toUpperCase();
        if (movieId.equals("0")) return;

        Optional<Movie> movieOpt = movies.stream().filter(m -> m.getMovieId().equalsIgnoreCase(movieId)).findFirst();
        if (movieOpt.isEmpty()) {
            System.out.println("Error: Invalid Movie ID.");
            return;
        }

        Movie selectedMovie = movieOpt.get();
        List<Show> availableShows = shows.stream()
                .filter(s -> s.getMovie().equals(selectedMovie))
                .sorted(Comparator.comparing(Show::getTime))
                .toList();

        if (availableShows.isEmpty()) {
            System.out.println("No shows scheduled for this movie yet.");
            return;
        }

        System.out.println("\n--- Schedules for: " + selectedMovie.getTitle() + " ---");
        availableShows.forEach(s -> System.out.printf("[%s] %-10s | %s\n", s.getShowId(), s.getScreenName(), s.getTime()));

        System.out.print("\nEnter Show ID to book (or 0 to back): ");
        String showId = sc.nextLine().trim().toUpperCase();
        if (showId.equals("0")) return;

        Optional<Show> showOpt = availableShows.stream().filter(s -> s.getShowId().equalsIgnoreCase(showId)).findFirst();
        if (showOpt.isEmpty()) {
            System.out.println("Error: Invalid Show ID.");
            return;
        }

        Show show = showOpt.get();
        show.displaySeatMap();

        System.out.print("Enter seat numbers separated by space (e.g., B1 B2 C4) or 0 to cancel: ");
        String seatInput = sc.nextLine().trim().toUpperCase();
        if (seatInput.equals("0")) return;

        List<String> requestedIds = Arrays.asList(seatInput.split("\\s+"));
        List<Seat> seatsToBook = show.getSeats().stream()
                .filter(seat -> requestedIds.contains(seat.getSeatId()))
                .toList();

        if (seatsToBook.size() != requestedIds.size()) {
            System.out.println("Error: One or more selected seat numbers are invalid.");
            return;
        }
        if (seatsToBook.stream().anyMatch(Seat::isBooked)) {
            System.out.println("Error: One or more selected seats are already booked [ XX ].");
            return;
        }

        double seatTotal = seatsToBook.stream().mapToDouble(s -> s.getTier().getPrice()).sum();
        List<SnackItem> snacksPicked = snackBar();
        double snackTotal = snacksPicked.stream().mapToDouble(SnackItem::getPrice).sum();
        double subTotal = seatTotal + snackTotal;

        // promo codes
        System.out.print("\nEnter Promo Code (Press Enter to skip): ");
        String promo = sc.nextLine().trim().toUpperCase();
        double discountAmt = 0;

        if (!promo.isEmpty()) {
            if (usedPromos.contains(promo)) {
                System.out.println("This promo code has already been used.");
            } else if (permanentPromos.containsKey(promo)) {
                discountAmt = subTotal * permanentPromos.get(promo);
                usedPromos.add(promo);
                System.out.printf("Promo applied! You saved Rs. %.2f\n", discountAmt);
            } else if (singleUsePromos.containsKey(promo)) {
                discountAmt = subTotal * singleUsePromos.get(promo);
                usedPromos.add(promo);
                singleUsePromos.remove(promo);
                System.out.printf("Bulk reward applied! You saved Rs. %.2f\n", discountAmt);
            } else {
                System.out.println("Invalid or expired promo code. Proceeding without discount.");
            }
        }

        // tax and billing
        double afterDiscount = subTotal - discountAmt;
        double gstRate = 0.18;
        double convenienceFee = 35.00;
        double gstAmt = afterDiscount * gstRate;
        double total = afterDiscount + gstAmt + convenienceFee;

        System.out.println("\nProcessing payment...");
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // bulk booking reward
        String earnedCode = null;
        if (seatsToBook.size() >= 5) {
            earnedCode = "BULK20-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            singleUsePromos.put(earnedCode, 0.20);
        }

        seatsToBook.forEach(Seat::book);
        show.addRevenue(total);

        Ticket ticket = new Ticket(show, seatsToBook, snacksPicked,
                seatTotal, snackTotal, discountAmt, gstAmt, convenienceFee, total, earnedCode);
        userWallet.add(ticket);

        System.out.println("PAYMENT SUCCESSFUL!");
        ticket.printTicket();
    }

    private static List<SnackItem> snackBar() {
        List<SnackItem> cart = new ArrayList<>();
        SnackItem[] items = SnackItem.values();

        while (true) {
            System.out.println("\n--- SNACK BAR ---");
            for (int i = 0; i < items.length; i++) {
                System.out.printf("%d. %-25s - Rs. %.2f\n", (i + 1), items[i].getDescription(), items[i].getPrice());
            }
            System.out.println("0. Proceed to Checkout");
            System.out.print("Select item to add: ");

            String input = sc.nextLine().trim();
            if (input.equals("0")) break;

            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < items.length) {
                    cart.add(items[idx]);
                    System.out.println("Added: " + items[idx].name());
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return cart;
    }

    private static void showWallet() {
        if (userWallet.isEmpty()) {
            System.out.println("\nYour wallet is empty. No active tickets.");
            return;
        }
        System.out.println("\n--- Your Active Tickets ---");
        userWallet.forEach(Ticket::printTicket);
    }

    private static void cancelTicket() {
        if (userWallet.isEmpty()) {
            System.out.println("\nYou have no active tickets to cancel.");
            return;
        }
        showWallet();
        System.out.print("Enter the exact Ticket ID to cancel (or 0 to exit): ");
        String targetId = sc.nextLine().trim().toUpperCase();
        if (targetId.equals("0")) return;

        Optional<Ticket> ticketOpt = userWallet.stream()
                .filter(t -> t.getTicketId().equalsIgnoreCase(targetId))
                .findFirst();

        if (ticketOpt.isEmpty()) {
            System.out.println("Error: Ticket ID not found in your wallet.");
            return;
        }

        Ticket ticket = ticketOpt.get();
        ticket.getBookedSeats().forEach(Seat::unbook);
        ticket.getShow().deductRevenue(ticket.getGrandTotal());
        userWallet.remove(ticket);

        System.out.println("\nTicket " + targetId + " cancelled successfully. Rs. " + ticket.getGrandTotal() + " refunded.");

        if (ticket.getPromoEarned() != null) {
            singleUsePromos.remove(ticket.getPromoEarned());
            System.out.println("Note: The bulk reward code (" + ticket.getPromoEarned() + ") from this booking has been revoked.");
        }
    }

    // ---- admin side ----

    private static void adminMenu() {
        while (true) {
            System.out.println("\n------------- ADMIN DASHBOARD -------------");
            System.out.println("1. View Multiplex Analytics");
            System.out.println("2. Add New Movie");
            System.out.println("3. Add New Show");
            System.out.println("4. Remove Movie");
            System.out.println("0. Back to Main Menu / Logout");
            System.out.print("Select Action: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> viewAnalytics();
                case "2" -> addMovie();
                case "3" -> addShow();
                case "4" -> removeMovie();
                case "0" -> { return; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void addMovie() {
        System.out.print("\nEnter Movie Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter Movie Genre: ");
        String genre = sc.nextLine().trim();

        String id = "M" + (++idCounter);
        movies.add(new Movie(id, title, genre));
        System.out.println("Movie Added Successfully! ID: " + id);
    }

    private static void addShow() {
        if (movies.isEmpty()) {
            System.out.println("No movies available. Add a movie first.");
            return;
        }
        movies.forEach(m -> System.out.printf("[%s] %s\n", m.getMovieId(), m.getTitle()));
        System.out.print("\nEnter Movie ID to create a show for: ");
        String movieId = sc.nextLine().trim().toUpperCase();

        Optional<Movie> movieOpt = movies.stream()
                .filter(m -> m.getMovieId().equalsIgnoreCase(movieId))
                .findFirst();
        if (movieOpt.isEmpty()) {
            System.out.println("Invalid Movie ID.");
            return;
        }

        System.out.print("Enter Screen Name (e.g., Screen 1): ");
        String screen = sc.nextLine().trim();
        System.out.print("Enter Time (e.g., 06:00 PM): ");
        String time = sc.nextLine().trim();

        String showId = "SH" + (++idCounter);
        shows.add(new Show(showId, movieOpt.get(), screen, time));
        System.out.println("Show Created! ID: " + showId);
    }

    private static void removeMovie() {
        if (movies.isEmpty()) {
            System.out.println("No movies to remove.");
            return;
        }
        movies.forEach(m -> System.out.printf("[%s] %s\n", m.getMovieId(), m.getTitle()));
        System.out.print("\nEnter Movie ID to remove: ");
        String movieId = sc.nextLine().trim().toUpperCase();

        Optional<Movie> movieOpt = movies.stream()
                .filter(m -> m.getMovieId().equalsIgnoreCase(movieId))
                .findFirst();
        if (movieOpt.isEmpty()) {
            System.out.println("Movie not found.");
            return;
        }

        Movie target = movieOpt.get();
        userWallet.removeIf(ticket -> ticket.getShow().getMovie().equals(target));
        shows.removeIf(s -> s.getMovie().equals(target));
        movies.remove(target);
        System.out.println("Movie and all associated shows/tickets removed.");
    }

    private static void viewAnalytics() {
        System.out.println("\n============= MULTIPLEX ANALYTICS =============");
        Map<Movie, List<Show>> grouped = shows.stream().collect(Collectors.groupingBy(Show::getMovie));
        double platformTotal = 0;

        for (var entry : grouped.entrySet()) {
            Movie movie = entry.getKey();
            List<Show> movieShows = entry.getValue();
            System.out.println("\nMOVIE: " + movie.getTitle().toUpperCase());

            double movieRevenue = 0;
            for (Show show : movieShows) {
                long sold = show.getSeats().stream().filter(Seat::isBooked).count();
                double rev = show.getTotalRevenue();
                movieRevenue += rev;
                System.out.printf("  [%s] %-10s | %s | Seats Sold: %02d/%d | Rev: Rs. %.2f\n",
                        show.getShowId(), show.getScreenName(), show.getTime(), sold, show.getSeats().size(), rev);
            }
            System.out.printf("  => Subtotal for %s: Rs. %.2f\n", movie.getTitle(), movieRevenue);
            platformTotal += movieRevenue;
        }
        System.out.println("\n=============================================");
        System.out.printf("GRAND PLATFORM REVENUE: Rs. %.2f\n", platformTotal);
        System.out.println("=============================================");
    }

    // seed data - preloaded movies and shows
    private static void loadSeedData() {
        Movie m1 = new Movie("M01", "The Dark Knight", "Action/Classic");
        Movie m2 = new Movie("M02", "Inception", "Sci-Fi/Classic");
        Movie m3 = new Movie("M03", "Interstellar", "Sci-Fi/Classic");
        Movie m4 = new Movie("M04", "Dune: Part Two", "Sci-Fi/Trending");
        Movie m5 = new Movie("M05", "Avatar: The Way of Water", "Fantasy/Trending");
        Movie m6 = new Movie("M06", "Spider-Man: Across the Spider-Verse", "Animation/Popular");
        Movie m7 = new Movie("M07", "Oppenheimer", "Biography/Trending");
        Movie m8 = new Movie("M08", "John Wick: Chapter 4", "Action/Popular");

        movies.addAll(List.of(m1, m2, m3, m4, m5, m6, m7, m8));

        int cnt = 101;

        // trending - heavy rotation
        shows.add(new Show("SH" + (cnt++), m4, "Screen 1", "09:00 AM"));
        shows.add(new Show("SH" + (cnt++), m4, "Screen 2", "11:30 AM"));
        shows.add(new Show("SH" + (cnt++), m4, "Screen 1", "02:30 PM"));
        shows.add(new Show("SH" + (cnt++), m4, "Screen 3", "05:00 PM"));
        shows.add(new Show("SH" + (cnt++), m4, "Screen 1", "08:00 PM"));
        shows.add(new Show("SH" + (cnt++), m4, "Screen 2", "10:30 PM"));

        shows.add(new Show("SH" + (cnt++), m7, "Screen 5", "09:30 AM"));
        shows.add(new Show("SH" + (cnt++), m7, "Screen 5", "01:00 PM"));
        shows.add(new Show("SH" + (cnt++), m7, "Screen 4", "04:30 PM"));
        shows.add(new Show("SH" + (cnt++), m7, "Screen 5", "06:45 PM"));
        shows.add(new Show("SH" + (cnt++), m7, "Screen 6", "10:00 PM"));

        shows.add(new Show("SH" + (cnt++), m5, "Screen 3", "10:00 AM"));
        shows.add(new Show("SH" + (cnt++), m5, "Screen 4", "02:00 PM"));
        shows.add(new Show("SH" + (cnt++), m5, "Screen 3", "08:30 PM"));
        shows.add(new Show("SH" + (cnt++), m5, "Screen 7", "11:00 PM"));

        // popular - standard rotation
        shows.add(new Show("SH" + (cnt++), m8, "Screen 6", "11:00 AM"));
        shows.add(new Show("SH" + (cnt++), m8, "Screen 6", "04:00 PM"));
        shows.add(new Show("SH" + (cnt++), m8, "Screen 4", "07:30 PM"));

        shows.add(new Show("SH" + (cnt++), m6, "Screen 7", "09:00 AM"));
        shows.add(new Show("SH" + (cnt++), m6, "Screen 7", "01:30 PM"));
        shows.add(new Show("SH" + (cnt++), m6, "Screen 2", "08:15 PM"));

        // classics - limited shows
        shows.add(new Show("SH" + (cnt++), m1, "Screen 8", "10:30 AM"));
        shows.add(new Show("SH" + (cnt++), m1, "Screen 8", "06:00 PM"));

        shows.add(new Show("SH" + (cnt++), m2, "Screen 8", "02:00 PM"));

        shows.add(new Show("SH" + (cnt++), m3, "Screen 8", "09:30 PM"));
    }
}
