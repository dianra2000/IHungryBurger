import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3308/iHungryBurger";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int insertOrderAndTransferDetails(Map<String, Integer> selectedBurgers) {
        String insertOrderQuery = "INSERT INTO orders (items, quantity) VALUES (?, ?)";
        String insertOrderDetailsQuery = "INSERT INTO order_details (order_id, item_name, quantity) VALUES (?, ?, ?)";

        int orderId = -1;

        try (Connection conn = getConnection();
             PreparedStatement insertOrderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertOrderDetailsStmt = conn.prepareStatement(insertOrderDetailsQuery)) {

            // Prepare the items and quantities for insertion
            StringBuilder itemsBuilder = new StringBuilder();
            StringBuilder quantityBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : selectedBurgers.entrySet()) {
                itemsBuilder.append(entry.getKey()).append(",");
                quantityBuilder.append(entry.getValue()).append(",");
            }
            String items = itemsBuilder.toString().replaceAll(",$", "");
            String quantities = quantityBuilder.toString().replaceAll(",$", "");


            insertOrderStmt.setString(1, items);
            insertOrderStmt.setString(2, quantities);
            insertOrderStmt.executeUpdate();

            // Retrieve generated order ID
            ResultSet rs = insertOrderStmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }


            insertOrderDetailsStmt.setInt(1, orderId);
            insertOrderDetailsStmt.setString(2, items);
            insertOrderDetailsStmt.setString(3, quantities);
            insertOrderDetailsStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }


    public static void addUser(String username, String password) {
        String insertUserQuery = "INSERT INTO login (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {

            insertStmt.setString(1, username);
            insertStmt.setString(2, password);

            insertStmt.executeUpdate();
            System.out.println("User added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUserCredentials(String username, String password) {
        String checkCredentialsQuery = "SELECT * FROM login WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkCredentialsQuery)) {

            checkStmt.setString(1, username);
            checkStmt.setString(2, password);

            ResultSet rs = checkStmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // Example: Insert an order and transfer it to the order_details table
        Map<String, Integer> selectedBurgers = Map.of(
                "Cheeseburger", 2,
                "Veggie Burger", 1
        );

        // Insert the order and get the generated order ID
        int orderId = insertOrderAndTransferDetails(selectedBurgers);


        if (orderId != -1) {
            System.out.println("Order placed successfully. Order ID: " + orderId);
        } else {
            System.out.println("Failed to place order.");
        }
    }
}