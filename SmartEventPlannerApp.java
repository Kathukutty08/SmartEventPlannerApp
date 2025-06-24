import java.sql.*;
import java.util.Scanner;

class DBHelper {
    static final String URL = "jdbc:mysql://localhost:3306/event_planner";
    static final String USER = "user";
    static final String PASS = "password"; // Add your password if any

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

class User {
    int id;
    String username;
    String role;

    public static User login(String username, String password) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.role = rs.getString("role");
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public static void register(String username, String password, String role) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            System.out.println("User registered!");
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }
}

class Event {
    public static void createEvent(int organizerId, String name, double budget, String venue, int size) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO events (organizer_id, event_name, budget, venue, audience_size) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, organizerId);
            stmt.setString(2, name);
            stmt.setDouble(3, budget);
            stmt.setString(4, venue);
            stmt.setInt(5, size);
            stmt.executeUpdate();
            System.out.println("Event created!");
        } catch (SQLException e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    public static void showEvents() {
        try (Connection conn = DBHelper.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM events");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        "Name: " + rs.getString("event_name") +
                        " Budget: " + rs.getDouble("budget") +
                        " Venue: " + rs.getString("venue"));
            }
        } catch (SQLException e) {
            System.out.println("Error showing events: " + e.getMessage());
        }
    }
}

class Feedback {
    public static void addFeedback(int eventId, int rating, String comment) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO feedback (event_id, rating, comment) VALUES (?, ?, ?)");
            stmt.setInt(1, eventId);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);
            stmt.executeUpdate();
            System.out.println("Feedback submitted!");
        } catch (SQLException e) {
            System.out.println("Feedback error: " + e.getMessage());
        }
    }
}

class Vendor {
    public static void addVendor(String name, String serviceType) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO vendors (name, service_type) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, serviceType);
            stmt.executeUpdate();
            System.out.println("Vendor added!");
        } catch (SQLException e) {
            System.out.println("Vendor error: " + e.getMessage());
        }
    }

    public static void assignVendorToEvent(int eventId, int vendorId) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO event_vendors (event_id, vendor_id) VALUES (?, ?)");
            stmt.setInt(1, eventId);
            stmt.setInt(2, vendorId);
            stmt.executeUpdate();
            System.out.println("Vendor assigned to event!");
        } catch (SQLException e) {
            System.out.println("Assignment error: " + e.getMessage());
        }
    }

    public static void showVendors() {
        try (Connection conn = DBHelper.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM vendors");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") + " | Service: " + rs.getString("service_type"));
            }
        } catch (SQLException e) {
            System.out.println("Vendor display error: " + e.getMessage());
        }
    }
}

class Payment {
    public static void makePayment(int eventId, double amount, String description) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO payments (event_id, amount, description) VALUES (?, ?, ?)");
            stmt.setInt(1, eventId);
            stmt.setDouble(2, amount);
            stmt.setString(3, description);
            stmt.executeUpdate();
            System.out.println("Payment recorded!");
        } catch (SQLException e) {
            System.out.println("Payment error: " + e.getMessage());
        }
    }
}

class Budget {
    public static void addExpense(int eventId, String item, double cost) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO expenses (event_id, item, cost) VALUES (?, ?, ?)");
            stmt.setInt(1, eventId);
            stmt.setString(2, item);
            stmt.setDouble(3, cost);
            stmt.executeUpdate();
            System.out.println("Expense recorded!");
        } catch (SQLException e) {
            System.out.println("Expense error: " + e.getMessage());
        }
    }

    public static void addRevenue(int eventId, String source, double amount) {
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO revenues (event_id, source, amount) VALUES (?, ?, ?)");
            stmt.setInt(1, eventId);
            stmt.setString(2, source);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            System.out.println("Revenue recorded!");
        } catch (SQLException e) {
            System.out.println("Revenue error: " + e.getMessage());
        }
    }

    public static void showBudgetReport(int eventId) {
        try (Connection conn = DBHelper.getConnection()) {
            double totalExpenses = 0, totalRevenue = 0;

            ResultSet rsExpenses = conn.createStatement().executeQuery("SELECT SUM(cost) FROM expenses WHERE event_id = " + eventId);
            if (rsExpenses.next()) totalExpenses = rsExpenses.getDouble(1);

            ResultSet rsRevenues = conn.createStatement().executeQuery("SELECT SUM(amount) FROM revenues WHERE event_id = " + eventId);
            if (rsRevenues.next()) totalRevenue = rsRevenues.getDouble(1);

            System.out.println("Event ID: " + eventId);
            System.out.println("Total Revenue: ₹" + totalRevenue);
            System.out.println("Total Expenses: ₹" + totalExpenses);
            System.out.println("Net Profit/Loss: ₹" + (totalRevenue - totalExpenses));
        } catch (SQLException e) {
            System.out.println("Budget report error: " + e.getMessage());
        }
    }
}

public class SmartEventPlannerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = null;

        System.out.println("1. Register\n2. Login");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Username: ");
        String uname = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (choice == 1) {
            System.out.print("Role (organizer/attendee): ");
            String role = sc.nextLine();
            User.register(uname, pass, role);
        }

        user = User.login(uname, pass);
        if (user == null) {
            System.out.println("Login failed.");
            return;
        }

        System.out.println("Welcome, " + user.username + " (" + user.role + ")");

        if (user.role.equalsIgnoreCase("organizer")) {
            System.out.println("1. Create Event\n2. Show Events\n3. Add Feedback\n4. Add Vendor\n5. Assign Vendor to Event\n6. Make Payment\n7. Add Expense\n8. Add Revenue\n9. Show Budget Report");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    System.out.print("Event Name: ");
                    String name = sc.nextLine();
                    System.out.print("Budget: ");
                    double budget = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Venue: ");
                    String venue = sc.nextLine();
                    System.out.print("Audience Size: ");
                    int size = sc.nextInt();
                    Event.createEvent(user.id, name, budget, venue, size);
                    break;

                case 2:
                    Event.showEvents();
                    break;

                case 3:
                    System.out.print("Event ID: ");
                    int eid = sc.nextInt();
                    System.out.print("Rating (1-5): ");
                    int rating = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String comment = sc.nextLine();
                    Feedback.addFeedback(eid, rating, comment);
                    break;

                case 4:
                    System.out.print("Vendor Name: ");
                    String vname = sc.nextLine();
                    System.out.print("Service Type: ");
                    String service = sc.nextLine();
                    Vendor.addVendor(vname, service);
                    break;

                case 5:
                    Vendor.showVendors();
                    System.out.print("Event ID: ");
                    int evId = sc.nextInt();
                    System.out.print("Vendor ID: ");
                    int vendorId = sc.nextInt();
                    Vendor.assignVendorToEvent(evId, vendorId);
                    break;

                case 6:
                    System.out.print("Event ID: ");
                    int peid = sc.nextInt();
                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    Payment.makePayment(peid, amount, desc);
                    break;

                case 7:
                    System.out.print("Event ID: ");
                    int expEid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Expense Item: ");
                    String item = sc.nextLine();
                    System.out.print("Cost: ");
                    double cost = sc.nextDouble();
                    Budget.addExpense(expEid, item, cost);
                    break;

                case 8:
                    System.out.print("Event ID: ");
                    int revEid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Revenue Source: ");
                    String source = sc.nextLine();
                    System.out.print("Amount: ");
                    double revAmt = sc.nextDouble();
                    Budget.addRevenue(revEid, source, revAmt);
                    break;

                case 9:
                    System.out.print("Event ID: ");
                    int budgetEid = sc.nextInt();
                    Budget.showBudgetReport(budgetEid);
                    break;
            }
        } else {
            Event.showEvents();
        }

        sc.close();
    }
}
