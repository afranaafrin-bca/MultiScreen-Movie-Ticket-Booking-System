import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BookingConfirmationGUI {

    public static void showConfirmation(
            int showtimeId,
            String selectedSeatNumbers,
            double ticketSubtotal,
            double convenienceFee,
            double gst,
            double totalAmount) {

        JFrame frame = new JFrame("Booking Confirmation");
        frame.setSize(750, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel(
                "MULTI SCREEN",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        JLabel confirmed = new JLabel(
                "BOOKING CONFIRMED",
                SwingConstants.CENTER
        );

        confirmed.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel headingPanel = new JPanel(
                new GridLayout(2, 1)
        );

        headingPanel.add(title);
        headingPanel.add(confirmed);

        frame.add(
                headingPanel,
                BorderLayout.NORTH
        );

        // =========================
        // BOOKING DETAILS
        // =========================

        JTextArea details = new JTextArea();

        details.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        details.setEditable(false);

        details.setMargin(
                new Insets(25, 35, 25, 20)
        );

        String sql =
                "SELECT m.title, t.theatre_name, " +
                        "s.screen_name, st.show_date, st.show_time " +
                        "FROM showtimes st " +
                        "JOIN movies m ON st.movie_id = m.id " +
                        "JOIN screens s ON st.screen_id = s.screen_id " +
                        "JOIN theatres t ON s.theatre_id = t.theatre_id " +
                        "WHERE st.showtime_id = ?";

        String movie = "";
        String theatre = "";
        String screen = "";
        String date = "";
        String time = "";

        // =========================
        // DATABASE
        // =========================

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/multiscreen_db",
                    "root",
                    ""
            );

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, showtimeId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                movie =
                        rs.getString("title");

                theatre =
                        rs.getString("theatre_name");

                screen =
                        rs.getString("screen_name");

                date =
                        rs.getString("show_date");

                time =
                        rs.getString("show_time");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Unable to load booking details:\n"
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        // =========================
        // POSTER
        // =========================

        JLabel posterLabel = new JLabel();

        String posterPath = "";

        if (movie.equalsIgnoreCase("Dada")) {

            posterPath = "/MoviePoster/Dada.jpg";

        } else if (movie.equalsIgnoreCase("Sirai")) {

            posterPath = "/MoviePoster/Sirai.jpg";


        } else {

            posterPath = "/MoviePoster/default.jpg";
        }

        java.net.URL posterURL =
                BookingConfirmationGUI.class.getResource(
                        posterPath
                );

        if (posterURL != null) {

            ImageIcon posterIcon =
                    new ImageIcon(posterURL);

            Image posterImage =
                    posterIcon.getImage().getScaledInstance(
                            280,
                            390,
                            Image.SCALE_SMOOTH
                    );

            posterLabel.setIcon(
                    new ImageIcon(posterImage)
            );

        } else {

            posterLabel.setText("Poster not found");

            posterLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );
        }

        JPanel posterPanel = new JPanel(
                new BorderLayout()
        );

        posterPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 30
                )
        );

        posterPanel.add(
                posterLabel,
                BorderLayout.CENTER
        );

        // =========================
        // DISPLAY DETAILS
        // =========================

        details.setText(

                "Movie       : " + movie + "\n\n" +

                        "Theatre     : " + theatre + "\n\n" +

                        "Screen      : " + screen + "\n\n" +

                        "Date        : " + date + "\n\n" +

                        "Time        : " + time + "\n\n" +

                        "Seats       : " + selectedSeatNumbers + "\n\n" +

                        "Ticket Subtotal : ₹" +
                        String.format("%.2f", ticketSubtotal) + "\n\n" +

                        "Convenience Fee : ₹" +
                        String.format("%.2f", convenienceFee) + "\n\n" +

                        "GST (18%)       : ₹" +
                        String.format("%.2f", gst) + "\n\n" +

                        "TOTAL AMOUNT    : ₹" +
                        String.format("%.2f", totalAmount) + "\n\n" +

                        "----------------------------------------\n\n" +

                        "       THANK YOU FOR BOOKING!\n"
        );

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(
                new BorderLayout()
        );

        centerPanel.add(
                details,
                BorderLayout.CENTER
        );

        centerPanel.add(
                posterPanel,
                BorderLayout.EAST
        );

        frame.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =========================
        // WINDOW SETTINGS
        // =========================

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        showConfirmation(
                1,
                "D4",
                200.0,
                60.0,
                10.80,
                270.80
        );
    }
}