import java.sql.*;
import java.util.Scanner;

public class InventoryManagementApp{
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establishing connection to the Oracle database
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");

            System.out.println("Connection established to Database");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Insert Item");
                System.out.println("2. Update Item");
                System.out.println("3. Delete Item");
                System.out.println("4. Display Available Items");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        insertItem(con, scanner);
                        break;
                    case 2:
                        updateItem(con, scanner);
                        break;
                    case 3:
                        deleteItem(con, scanner);
                        break;
                    case 4:
                        displayAvailableItems(con);
                        break;
                    case 5:
                        con.close();
                        scanner.close();
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertItem(Connection con, Scanner scanner) throws SQLException {
        String query = "INSERT INTO Item (item_no, item_name, item_price, qty_on_hand) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Item Number: ");
        int itemNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Item Price: ");
        double itemPrice = scanner.nextDouble();
        System.out.print("Enter Quantity on Hand: ");
        int qtyOnHand = scanner.nextInt();

        stmt.setInt(1, itemNo);
        stmt.setString(2, itemName);
        stmt.setDouble(3, itemPrice);
        stmt.setInt(4, qtyOnHand);

        int rowsInserted = stmt.executeUpdate();
        System.out.println(rowsInserted + " records inserted");

        stmt.close();
    }

    private static void updateItem(Connection con, Scanner scanner) throws SQLException {
        String query = "UPDATE Item SET item_name = ?, item_price = ?, qty_on_hand = ? WHERE item_no = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Item Number to update: ");
        int itemNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter new Item Price: ");
        double itemPrice = scanner.nextDouble();
        System.out.print("Enter new Quantity on Hand: ");
        int qtyOnHand = scanner.nextInt();

        stmt.setString(1, itemName);
        stmt.setDouble(2, itemPrice);
        stmt.setInt(3, qtyOnHand);
        stmt.setInt(4, itemNo);

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated + " records updated");

        stmt.close();
    }

    private static void deleteItem(Connection con, Scanner scanner) throws SQLException {
        String query = "DELETE FROM Item WHERE item_no = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Item Number to delete: ");
        int itemNo = scanner.nextInt();

        stmt.setInt(1, itemNo);

        int rowsDeleted = stmt.executeUpdate();
        System.out.println(rowsDeleted + " records deleted");

        stmt.close();
    }

    private static void displayAvailableItems(Connection con) throws SQLException {
        String query = "SELECT item_no, item_name, item_price, qty_on_hand FROM Item WHERE qty_on_hand > 0";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Available Items:");
        while (rs.next()) {
            int itemNo = rs.getInt("item_no");
            String itemName = rs.getString("item_name");
            double itemPrice = rs.getDouble("item_price");
            int qtyOnHand = rs.getInt("qty_on_hand");

            System.out.println("Item No: " + itemNo + ", Name: " + itemName + ", Price: " + itemPrice + ", Quantity on Hand: " + qtyOnHand);
        }

        rs.close();
        stmt.close();
    }
}
