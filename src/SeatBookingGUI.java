import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SeatBookingGUI {

    static int showtimeId;

    public static void main(String[] args) {

        if (showtimeId <= 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No showtime selected."
            );
            return;
        }

        Connection con = null;

        try {

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/multiscreen_db",
                    "root",
                    ""
            );

            System.out.println("Database connected successfully!");

            // -------------------------------------------------
            // GET BOOKED SEATS FOR THIS SHOWTIME
            // -------------------------------------------------

            String bookedSql =
                    "SELECT s.seat_number " +
                            "FROM seats s " +
                            "JOIN bookings b ON s.seat_id = b.seat_id " +
                            "WHERE b.showtime_id = ?";

            PreparedStatement bookedPs =
                    con.prepareStatement(bookedSql);

            bookedPs.setInt(1, showtimeId);

            ResultSet bookedRs = bookedPs.executeQuery();

            Set<String> bookedSeats = new HashSet<>();

            while (bookedRs.next()) {

                bookedSeats.add(
                        bookedRs.getString("seat_number")
                );
            }

            bookedRs.close();
            bookedPs.close();

            // -------------------------------------------------
            // GET SEAT IDs FOR THIS SHOWTIME'S SCREEN
            // -------------------------------------------------

            String seatSql =
                    "SELECT s.seat_id, s.seat_number " +
                            "FROM seats s " +
                            "JOIN showtimes st ON s.screen_id = st.screen_id " +
                            "WHERE st.showtime_id = ?";

            PreparedStatement seatPs =
                    con.prepareStatement(seatSql);

            seatPs.setInt(1, showtimeId);

            ResultSet seatRs = seatPs.executeQuery();

            Map<String, Integer> seatIds = new HashMap<>();

            while (seatRs.next()) {

                seatIds.put(
                        seatRs.getString("seat_number"),
                        seatRs.getInt("seat_id")
                );
            }

            seatRs.close();
            seatPs.close();

            // -------------------------------------------------
            // FRAME
            // -------------------------------------------------

            JFrame frame =
                    new JFrame("Multi Screen - Seat Booking");

            frame.setSize(1000, 700);

            frame.setLocationRelativeTo(null);

            frame.setDefaultCloseOperation(
                    JFrame.DISPOSE_ON_CLOSE
            );

            frame.setLayout(new BorderLayout());

            // -------------------------------------------------
            // MAIN PANEL
            // -------------------------------------------------

            JPanel mainPanel = new JPanel();

            mainPanel.setLayout(
                    new BoxLayout(
                            mainPanel,
                            BoxLayout.Y_AXIS
                    )
            );

            ArrayList<JButton> selectedSeats =
                    new ArrayList<>();
            final double CLASSIC_PRICE = 90.0;
            final double PREMIUM_PRICE = 200.0;
            final double CONVENIENCE_FEE = 60.0;
            final double GST_RATE = 0.18;

            // -------------------------------------------------
            // SCREEN
            // -------------------------------------------------

            JLabel screenLabel =
                    new JLabel("All Eyes Here");

            screenLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            16
                    )
            );

            screenLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            mainPanel.add(
                    Box.createVerticalStrut(15)
            );

            mainPanel.add(screenLabel);

            mainPanel.add(
                    Box.createVerticalStrut(25)
            );

            // -------------------------------------------------
            // CLASSIC
            // -------------------------------------------------

            JLabel classicLabel =
                    new JLabel("CLASSIC");

            classicLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            22
                    )
            );

            classicLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            mainPanel.add(classicLabel);

            mainPanel.add(
                    Box.createVerticalStrut(15)
            );

            // Rows A, B, C
            for (char row = 'A'; row <= 'C'; row++) {

                JPanel rowPanel = new JPanel();

                rowPanel.setLayout(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                6,
                                5
                        )
                );

                JLabel rowLabel =
                        new JLabel(
                                String.valueOf(row)
                        );

                rowLabel.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                18
                        )
                );

                rowPanel.add(rowLabel);

                // Seats 1 to 10
                for (int seat = 1; seat <= 10; seat++) {

                    String seatNumber =
                            row + String.valueOf(seat);

                    JButton seatButton =
                            new JButton(seatNumber);

                    seatButton.setPreferredSize(
                            new Dimension(45, 30)
                    );

                    seatButton.setMargin(
                            new Insets(0, 0, 0, 0)
                    );

                    seatButton.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    12
                            )
                    );

                    // Already booked
                    if (bookedSeats.contains(seatNumber)) {

                        seatButton.setBackground(
                                Color.GRAY
                        );

                        seatButton.setForeground(
                                Color.WHITE
                        );

                        seatButton.setEnabled(false);

                    } else {

                        seatButton.addActionListener(e -> {

                            if (selectedSeats.contains(
                                    seatButton
                            )) {

                                seatButton.setBackground(null);

                                selectedSeats.remove(
                                        seatButton
                                );

                            } else {

                                seatButton.setBackground(
                                        Color.GREEN
                                );

                                selectedSeats.add(
                                        seatButton
                                );
                            }

                        });
                    }

                    rowPanel.add(seatButton);
                }

                mainPanel.add(rowPanel);
            }

            // -------------------------------------------------
            // SPACE
            // -------------------------------------------------

            mainPanel.add(
                    Box.createVerticalStrut(25)
            );

            // -------------------------------------------------
            // PREMIUM
            // -------------------------------------------------

            JLabel premiumLabel =
                    new JLabel("PREMIUM");

            premiumLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            22
                    )
            );

            premiumLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            mainPanel.add(premiumLabel);

            mainPanel.add(
                    Box.createVerticalStrut(15)
            );

            // Rows D to K
            for (char row = 'D'; row <= 'K'; row++) {

                JPanel rowPanel = new JPanel();

                rowPanel.setLayout(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                6,
                                5
                        )
                );

                JLabel rowLabel =
                        new JLabel(
                                String.valueOf(row)
                        );

                rowLabel.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                18
                        )
                );

                rowPanel.add(rowLabel);

                // Seats 1 to 3
                for (int seat = 1; seat <= 3; seat++) {

                    String seatNumber =
                            row + String.valueOf(seat);

                    JButton seatButton =
                            new JButton(seatNumber);

                    seatButton.setPreferredSize(
                            new Dimension(45, 30)
                    );

                    seatButton.setMargin(
                            new Insets(0, 0, 0, 0)
                    );

                    seatButton.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    12
                            )
                    );

                    if (bookedSeats.contains(seatNumber)) {

                        seatButton.setBackground(
                                Color.GRAY
                        );

                        seatButton.setForeground(
                                Color.WHITE
                        );

                        seatButton.setEnabled(false);

                    } else {

                        seatButton.addActionListener(e -> {

                            if (selectedSeats.contains(
                                    seatButton
                            )) {

                                seatButton.setBackground(null);

                                selectedSeats.remove(
                                        seatButton
                                );

                            } else {

                                seatButton.setBackground(
                                        Color.GREEN
                                );

                                selectedSeats.add(
                                        seatButton
                                );
                            }

                        });
                    }

                    rowPanel.add(seatButton);
                }

                // Walking area
                rowPanel.add(
                        Box.createHorizontalStrut(40)
                );

                // Seats 4 to 7
                for (int seat = 4; seat <= 7; seat++) {

                    String seatNumber =
                            row + String.valueOf(seat);

                    JButton seatButton =
                            new JButton(seatNumber);

                    seatButton.setPreferredSize(
                            new Dimension(45, 30)
                    );

                    seatButton.setMargin(
                            new Insets(0, 0, 0, 0)
                    );

                    seatButton.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    12
                            )
                    );

                    if (bookedSeats.contains(seatNumber)) {

                        seatButton.setBackground(
                                Color.GRAY
                        );

                        seatButton.setForeground(
                                Color.WHITE
                        );

                        seatButton.setEnabled(false);

                    } else {

                        seatButton.addActionListener(e -> {

                            if (selectedSeats.contains(
                                    seatButton
                            )) {

                                seatButton.setBackground(null);

                                selectedSeats.remove(
                                        seatButton
                                );

                            } else {

                                seatButton.setBackground(
                                        Color.GREEN
                                );

                                selectedSeats.add(
                                        seatButton
                                );
                            }

                        });
                    }

                    rowPanel.add(seatButton);
                }

                // Walking area
                rowPanel.add(
                        Box.createHorizontalStrut(40)
                );

                // Seats 8 to 10
                for (int seat = 8; seat <= 10; seat++) {

                    String seatNumber =
                            row + String.valueOf(seat);

                    JButton seatButton =
                            new JButton(seatNumber);

                    seatButton.setPreferredSize(
                            new Dimension(45, 30)
                    );

                    seatButton.setMargin(
                            new Insets(0, 0, 0, 0)
                    );

                    seatButton.setFont(
                            new Font(
                                    "Arial",
                                    Font.BOLD,
                                    12
                            )
                    );

                    if (bookedSeats.contains(seatNumber)) {

                        seatButton.setBackground(
                                Color.GRAY
                        );

                        seatButton.setForeground(
                                Color.WHITE
                        );

                        seatButton.setEnabled(false);

                    } else {

                        seatButton.addActionListener(e -> {

                            if (selectedSeats.contains(
                                    seatButton
                            )) {

                                seatButton.setBackground(null);

                                selectedSeats.remove(
                                        seatButton
                                );

                            } else {

                                seatButton.setBackground(
                                        Color.GREEN
                                );

                                selectedSeats.add(
                                        seatButton
                                );
                            }

                        });
                    }

                    rowPanel.add(seatButton);
                }

                mainPanel.add(rowPanel);
            }

            // -------------------------------------------------
            // SCROLL PANE
            // -------------------------------------------------

            JScrollPane scrollPane =
                    new JScrollPane(mainPanel);

            frame.add(
                    scrollPane,
                    BorderLayout.CENTER
            );

            // -------------------------------------------------
            // BOTTOM PANEL
            // -------------------------------------------------

            JPanel bottomPanel =
                    new JPanel();

            JButton confirmButton =
                    new JButton("Confirm Booking");

            confirmButton.setPreferredSize(
                    new Dimension(180, 40)
            );
            final Connection bookingCon = con;
            confirmButton.addActionListener(e -> {

                if (selectedSeats.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select at least one seat."
                    );

                    return;
                }
                double ticketSubtotal = 0.0;

                for (JButton seatButton : selectedSeats) {

                    String seatNumber = seatButton.getText();

                    char row = seatNumber.charAt(0);

                    if (row >= 'A' && row <= 'C') {
                        ticketSubtotal += CLASSIC_PRICE;
                    } else if (row >= 'D' && row <= 'K') {
                        ticketSubtotal += PREMIUM_PRICE;
                    }
                }

                double gst = CONVENIENCE_FEE * GST_RATE;

                double totalAmount =
                        ticketSubtotal + CONVENIENCE_FEE + gst;

                try {

                    bookingCon.setAutoCommit(false);

                    String insertSql =
                            "INSERT INTO bookings " +
                                    "(showtime_id, seat_id) " +
                                    "VALUES (?, ?)";

                    PreparedStatement insertPs =
                            bookingCon.prepareStatement(insertSql);

                    for (JButton seatButton :
                            selectedSeats) {

                        String seatNumber =
                                seatButton.getText();

                        Integer seatId =
                                seatIds.get(seatNumber);

                        if (seatId == null) {

                            throw new SQLException(
                                    "Seat not found: "
                                            + seatNumber
                            );
                        }

                        insertPs.setInt(
                                1,
                                showtimeId
                        );

                        insertPs.setInt(
                                2,
                                seatId
                        );

                        insertPs.addBatch();
                    }

                    insertPs.executeBatch();

                    bookingCon.commit();

                    insertPs.close();

                    // Make selected seats gray
                    for (JButton seatButton :
                            selectedSeats) {

                        seatButton.setBackground(
                                Color.GRAY
                        );

                        seatButton.setForeground(
                                Color.WHITE
                        );

                        seatButton.setEnabled(false);
                    }

                    // Get selected seat numbers before clearing the list
                    StringBuilder selectedSeatNumbers =
                            new StringBuilder();

                    for (JButton seatButton : selectedSeats) {

                        if (selectedSeatNumbers.length() > 0) {
                            selectedSeatNumbers.append(", ");
                        }

                        selectedSeatNumbers.append(
                                seatButton.getText()
                        );
                    }

                    selectedSeats.clear();

                    JOptionPane.showMessageDialog(
                            frame,
                            "Booking confirmed successfully!"
                    );

                    BookingConfirmationGUI.showConfirmation(
                            showtimeId,
                            selectedSeatNumbers.toString(),
                            ticketSubtotal,
                            CONVENIENCE_FEE,
                            gst,
                            totalAmount
                    );

                } catch (SQLException ex) {

                    try {
                        bookingCon.rollback();
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(
                            frame,
                            "Booking failed:\n"
                                    + ex.getMessage()
                    );

                    ex.printStackTrace();
                }
            });

            bottomPanel.add(confirmButton);

            frame.add(
                    bottomPanel,
                    BorderLayout.SOUTH
            );

            System.out.println("Opening seat window...");
            frame.setVisible(true);

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Database connection failed:\n"
                            + e.getMessage()
            );
        }
    }
}