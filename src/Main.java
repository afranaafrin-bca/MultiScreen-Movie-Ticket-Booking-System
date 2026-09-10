import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);



        String url = "jdbc:mysql://localhost:3306/multiscreen_db";
        String username = "root";
        String password = "";

        try {

            Connection con = DriverManager.getConnection(url, username, password);

            System.out.println("Database connected successfully!");
            System.out.println("========== MULTI SCREEN ==========");
            System.out.println("1. Add Movie");
            System.out.println("2. View Movies");
            System.out.println("3. Add Theatre");
            System.out.println("4. View Theatres");
            System.out.println("5. Add Screen");
            System.out.println("6. View Screens");
            System.out.println("7. Add Show Time");
            System.out.println("8. View Show Times");
            System.out.println("9. Create Seats");
            System.out.println("10. View Seats");
            System.out.println("11. Select Show Time");
            System.out.println("12. Cancel Booking");
            System.out.println("13.View Bookings");
            System.out.println("14. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    scan.nextLine();


            String sql = "INSERT INTO movies (title, genre, language, duration, rating) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter movie title: ");
            String title = scan.nextLine();

            System.out.print("Enter genre: ");
            String genre = scan.nextLine();

            System.out.print("Enter language: ");
            String language = scan.nextLine();

            System.out.print("Enter duration: ");
            int duration = scan.nextInt();

            System.out.println("Enter rating: ");
            double rating = scan.nextDouble();
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setString(3, language);
            ps.setInt(4, duration);
            ps.setDouble(5, rating);
            int rows = ps.executeUpdate();

            System.out.println(rows + " movie added!");
            break;
                case 2:
            String sql2 = "SELECT * FROM movies";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Genre: " + rs.getString("genre"));
                System.out.println("Language: " + rs.getString("language"));
                System.out.println("Duration: " + rs.getInt("duration"));
                System.out.println("Rating: " + rs.getDouble("rating"));

            }
            break;
                case 3:

                    scan.nextLine();

                    String sql5 = "INSERT INTO theatres (theatre_name, location) VALUES (?, ?)";

                    PreparedStatement ps5 = con.prepareStatement(sql5);

                    System.out.print("Enter theatre name: ");
                    String theatreName = scan.nextLine();

                    System.out.print("Enter location: ");
                    String location = scan.nextLine();

                    ps5.setString(1, theatreName);
                    ps5.setString(2, location);

                    int theatreRows = ps5.executeUpdate();

                    System.out.println(theatreRows + " theatre added!");

                    break;
                case 4:

                    String sql6 = "SELECT * FROM theatres";

                    PreparedStatement ps6 = con.prepareStatement(sql6);

                    ResultSet rs2 = ps6.executeQuery();

                    while (rs2.next()) {

                        System.out.println("Theatre ID: " + rs2.getInt("theatre_id"));
                        System.out.println("Theatre Name: " + rs2.getString("theatre_name"));
                        System.out.println("Location: " + rs2.getString("location"));
                        System.out.println("------------------------");
                    }

                    break;
                case 5:

                    scan.nextLine();

                    String sql7 = "INSERT INTO screens (theatre_id, screen_name, total_seats) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement ps7 = con.prepareStatement(sql7);

                    System.out.print("Enter theatre ID: ");
                    int theatreId = scan.nextInt();
                    scan.nextLine();

                    System.out.print("Enter screen Name: ");
                    String screenName = scan.nextLine();

                    System.out.print("Enter total seats: ");
                    int totalSeats = scan.nextInt();

                    ps7.setInt(1, theatreId);
                    ps7.setString(2, screenName);
                    ps7.setInt(3, totalSeats);

                    int screenRows = ps7.executeUpdate();

                    System.out.println(screenRows + " screen added!");

                    break;
                case 6:

                    String sql8 = "SELECT * FROM screens";

                    PreparedStatement ps8 = con.prepareStatement(sql8);

                    ResultSet rs3 = ps8.executeQuery();

                    while (rs3.next()) {

                        System.out.println("Screen ID: " + rs3.getInt("screen_id"));
                        System.out.println("Theatre ID: " + rs3.getInt("theatre_id"));
                        System.out.println("Screen Name: " + rs3.getString("screen_name"));
                        System.out.println("Total Seats: " + rs3.getInt("total_seats"));
                        System.out.println("------------------------");
                    }

                    break;
                case 7:

                    scan.nextLine();

                    String sql9 = "INSERT INTO showtimes (movie_id, screen_id, show_date, show_time) " +
                            "VALUES (?, ?, ?, ?)";

                    PreparedStatement ps9 = con.prepareStatement(sql9);

                    System.out.print("Enter movie ID: ");
                    int movieId = scan.nextInt();

                    System.out.print("Enter screen ID: ");
                    int screenId = scan.nextInt();

                    scan.nextLine();

                    System.out.print("Enter show date (YYYY-MM-DD): ");
                    String showDate = scan.nextLine();

                    System.out.print("Enter show time (HH:MM:SS): ");
                    String showTime = scan.nextLine();

                    ps9.setInt(1, movieId);
                    ps9.setInt(2, screenId);
                    ps9.setString(3, showDate);
                    ps9.setString(4, showTime);

                    int showRows = ps9.executeUpdate();

                    System.out.println(showRows + " show time added!");

                    break;
                case 8:

                    String sql10 = "SELECT * FROM showtimes";

                    PreparedStatement ps10 = con.prepareStatement(sql10);

                    ResultSet rs4 = ps10.executeQuery();

                    while (rs4.next()) {

                        System.out.println("Showtime ID: " + rs4.getInt("showtime_id"));
                        System.out.println("Movie ID: " + rs4.getInt("movie_id"));
                        System.out.println("Screen ID: " + rs4.getInt("screen_id"));
                        System.out.println("Show Date: " + rs4.getDate("show_date"));
                        System.out.println("Show Time: " + rs4.getTime("show_time"));
                        System.out.println("------------------------");
                    }

                    break;
                case 9:

                    System.out.print("Enter screen ID: ");
                    int screenIdForSeats = scan.nextInt();

                    String sql11 = "INSERT INTO seats (screen_id, seat_number, seat_type) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement ps11 = con.prepareStatement(sql11);

                    // Classic seats - Rows A, B,
                    for (char row = 'A'; row <= 'C'; row++) {

                        for (int seat = 1; seat <= 10; seat++) {

                            String seatNumber = row + String.valueOf(seat);

                            ps11.setInt(1, screenIdForSeats);
                            ps11.setString(2, seatNumber);
                            ps11.setString(3, "Classic");

                            ps11.executeUpdate();
                        }
                    }

                    // Premium seats - Rows D, E, F
                    for (char row = 'D'; row <= 'K'; row++) {

                        for (int seat = 1; seat <= 10; seat++) {

                            String seatNumber = row + String.valueOf(seat);

                            ps11.setInt(1, screenIdForSeats);
                            ps11.setString(2, seatNumber);
                            ps11.setString(3, "Premium");

                            ps11.executeUpdate();
                        }
                    }

                    System.out.println("100 seats created successfully!");

                    break;
                case 10:

                    System.out.print("Enter Showtime ID: ");
                    int selectedShowtime = scan.nextInt();

                    // Get the screen ID for this showtime
                    String sql13 = "SELECT screen_id FROM showtimes WHERE showtime_id = ?";

                    PreparedStatement ps13 = con.prepareStatement(sql13);

                    ps13.setInt(1, selectedShowtime);

                    ResultSet rs6 = ps13.executeQuery();

                    if (!rs6.next()) {
                        System.out.println("Showtime not found!");
                        break;
                    }

                    int selectedScreen = rs6.getInt("screen_id");

                    // Check seats and their booking status
                    String sql14 = "SELECT s.seat_id, s.seat_number, s.seat_type, " +
                            "CASE WHEN b.booking_id IS NULL THEN 'AVAILABLE' " +
                            "ELSE 'BOOKED' END AS seat_status " +
                            "FROM seats s " +
                            "LEFT JOIN bookings b ON s.seat_id = b.seat_id " +
                            "AND b.showtime_id = ? " +
                            "WHERE s.screen_id = ? " +
                            "ORDER BY s.seat_number";

                    PreparedStatement ps14 = con.prepareStatement(sql14);

                    ps14.setInt(1, selectedShowtime);
                    ps14.setInt(2, selectedScreen);

                    ResultSet rs7 = ps14.executeQuery();
                    Set<String> bookedSeats = new HashSet<>();

                    while (rs7.next()) {

                        if (rs7.getString("seat_status").equals("BOOKED")) {
                            bookedSeats.add(rs7.getString("seat_number"));
                        }
                    }
                    System.out.println();
                    System.out.println("          ---------------------");
                    System.out.println("                 SCREEN");
                    System.out.println();


                    System.out.println();
                    System.out.println("                 CLASSIC");
                    System.out.println();

                    // Rows A, B, C
                    for (char row = 'A'; row <= 'C'; row++) {

                        System.out.print(row + "    ");

                        for (int seat = 1; seat <= 8; seat++) {

                            String seatNumber = row + String.valueOf(seat);
                            if (bookedSeats.contains(seatNumber)) {
                                System.out.print("[X] ");
                            } else {
                                System.out.print("[" + seatNumber + "] ");
                            }



                            if (seat == 4) {
                                System.out.print("    ");
                            }
                        }

                        System.out.println();
                    }

                    System.out.println();
                    System.out.println("                 PREMIUM");
                    System.out.println();

                    // Rows D, E, F
                    for (char row = 'D'; row <= 'K'; row++) {

                        System.out.print(row + "    ");

                        for (int seat = 1; seat <= 10; seat++) {

                            String seatNumber = row + String.valueOf(seat);

                            if (bookedSeats.contains(seatNumber)) {
                                System.out.print("[X] ");
                            } else {
                                System.out.print("[" + seatNumber + "] ");
                            }

                            if (seat == 4) {
                                System.out.print("    ");
                            }
                        }

                        System.out.println();
                    }


                    break;
                case 11:

                    String sql15 =
                            "SELECT st.showtime_id, m.title, t.theatre_name, " +
                                    "s.screen_name, st.show_date, st.show_time " +
                                    "FROM showtimes st " +
                                    "JOIN movies m ON st.movie_id = m.id " +
                                    "JOIN screens s ON st.screen_id = s.screen_id " +
                                    "JOIN theatres t ON s.theatre_id = t.theatre_id";

                    PreparedStatement ps15 = con.prepareStatement(sql15);

                    ResultSet rs5 = ps15.executeQuery();

                    System.out.println();
                    System.out.println("========== SELECT SHOW TIME ==========");

                    int option = 1;

                    while (rs5.next()) {

                        System.out.println(option + ". " + rs5.getString("title"));
                        System.out.println("   Theatre: " + rs5.getString("theatre_name"));
                        System.out.println("   Screen: " + rs5.getString("screen_name"));
                        System.out.println("   Date: " + rs5.getDate("show_date"));
                        System.out.println("   Time: " + rs5.getTime("show_time"));
                        System.out.println();

                        option++;
                    }
                    System.out.print("Enter your choice: ");
                    int selectedOption = scan.nextInt();
                    rs5 = ps15.executeQuery();

                    int currentOption = 1;
                    int selectedShowtimeId = -1;

                    while (rs5.next()) {

                        if (currentOption == selectedOption) {

                            selectedShowtimeId = rs5.getInt("showtime_id");
                            break;
                        }
                        currentOption++;
                    }
                            if (selectedShowtimeId != -1) {

                                System.out.println("Selected showtime successfully!");
                                System.out.println("Showtime ID: " + selectedShowtimeId);
                                SeatBookingGUI.showtimeId = selectedShowtimeId;
                                SeatBookingGUI.main(new String[]{});

                            } else {

                                System.out.println("Invalid choice!");
                            }
                            rs5.close();
                            ps15.close();
                            break;
                case 12:

                    scan.nextLine();

                    System.out.print("Enter Seat Number to cancel: ");
                    String cancelSeatNumber = scan.nextLine();

                    // First find the seat_id
                    String seatSql =
                            "SELECT seat_id FROM seats WHERE seat_number = ? LIMIT 1";

                    PreparedStatement seatPs =
                            con.prepareStatement(seatSql);

                    seatPs.setString(1, cancelSeatNumber);

                    ResultSet seatRs = seatPs.executeQuery();

                    if (seatRs.next()) {

                        int cancelSeatId = seatRs.getInt("seat_id");

                        seatRs.close();
                        seatPs.close();

                        // Delete booking using seat_id
                        String cancelSql =
                                "DELETE FROM bookings WHERE seat_id = ?";

                        PreparedStatement cancelPs =
                                con.prepareStatement(cancelSql);

                        cancelPs.setInt(1, cancelSeatId);

                        int deletedRows =
                                cancelPs.executeUpdate();

                        if (deletedRows > 0) {

                            System.out.println(
                                    "Booking for seat " +
                                            cancelSeatNumber +
                                            " cancelled successfully!"
                            );

                        } else {

                            System.out.println(
                                    "No booking found for seat " +
                                            cancelSeatNumber
                            );
                        }

                        cancelPs.close();

                    } else {

                        seatRs.close();
                        seatPs.close();

                        System.out.println(
                                "Seat " +
                                        cancelSeatNumber +
                                        " does not exist!"
                        );
                    }

                    break;
                case 13:

                    String sql16 =
                            "SELECT b.booking_id, " +
                                    "m.title, " +
                                    "t.theatre_name, " +
                                    "s.screen_name, " +
                                    "st.show_date, " +
                                    "st.show_time, " +
                                    "se.seat_number " +
                                    "FROM bookings b " +
                                    "JOIN showtimes st ON b.showtime_id = st.showtime_id " +
                                    "JOIN movies m ON st.movie_id = m.id " +
                                    "JOIN screens s ON st.screen_id = s.screen_id " +
                                    "JOIN theatres t ON s.theatre_id = t.theatre_id " +
                                    "JOIN seats se ON b.seat_id = se.seat_id " +
                                    "ORDER BY b.booking_id";

                    PreparedStatement ps16 =
                            con.prepareStatement(sql16);

                    ResultSet rs8 =
                            ps16.executeQuery();

                    System.out.println();
                    System.out.println("========== MY BOOKINGS ==========");

                    boolean hasBookings = false;

                    while (rs8.next()) {

                        hasBookings = true;

                        System.out.println("Booking ID : " +
                                rs8.getInt("booking_id"));

                        System.out.println("Movie      : " +
                                rs8.getString("title"));

                        System.out.println("Theatre    : " +
                                rs8.getString("theatre_name"));

                        System.out.println("Screen     : " +
                                rs8.getString("screen_name"));

                        System.out.println("Date       : " +
                                rs8.getDate("show_date"));

                        System.out.println("Time       : " +
                                rs8.getTime("show_time"));

                        System.out.println("Seat       : " +
                                rs8.getString("seat_number"));

                        System.out.println("----------------------------------");
                    }

                    if (!hasBookings) {

                        System.out.println("No bookings found.");

                    }

                    rs8.close();
                    ps16.close();

                    break;
                    case 14:

                    System.out.println(
                            "Thank you for using Multi Screen!"
                    );

                    break;
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
