import java.sql.*;
import java.util.Scanner;

public class SalesManagementApp{
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
                System.out.println("1. Add Salesman");
                System.out.println("2. Update Salesman Commission");
                System.out.println("3. Delete Salesman");
                System.out.println("4. Find Total Orders by Customer under a Salesman");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addSalesman(con, scanner);
                        break;
                    case 2:
                        updateCommission(con, scanner);
                        break;
                    case 3:
                        deleteSalesman(con, scanner);
                        break;
                    case 4:
                        findTotalOrders(con, scanner);
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

    private static void addSalesman(Connection con, Scanner scanner) throws SQLException {
        String query = "INSERT INTO Salesman (S1d, Sname, City, Commission) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Salesman ID: ");
        int sid = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Salesman Name: ");
        String sname = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter Commission: ");
        double commission = scanner.nextDouble();

        stmt.setInt(1, sid);
        stmt.setString(2, sname);
        stmt.setString(3, city);
        stmt.setDouble(4, commission);

        int rowsInserted = stmt.executeUpdate();
        System.out.println(rowsInserted + " records inserted");

        stmt.close();
    }

    private static void updateCommission(Connection con, Scanner scanner) throws SQLException {
        String query = "UPDATE Salesman SET Commission = ? WHERE S1d = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Salesman ID to update commission: ");
        int sid = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Commission: ");
        double commission = scanner.nextDouble();

        stmt.setDouble(1, commission);
        stmt.setInt(2, sid);

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated + " records updated");

        stmt.close();
    }

    private static void deleteSalesman(Connection con, Scanner scanner) throws SQLException {
        String query = "DELETE FROM Salesman WHERE S1d = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Salesman ID to delete: ");
        int sid = scanner.nextInt();

        stmt.setInt(1, sid);

        int rowsDeleted = stmt.executeUpdate();
        System.out.println(rowsDeleted + " records deleted");

        stmt.close();
    }

    private static void findTotalOrders(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter Salesman ID: ");
        int sid = scanner.nextInt();
        System.out.print("Enter Customer ID: ");
        int cid = scanner.nextInt();

        String query = "SELECT COUNT(*) AS total_orders FROM Customersale WHERE Sid = ? AND c_no = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, sid);
        stmt.setInt(2, cid);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int totalOrders = rs.getInt("total_orders");
            System.out.println("Total orders by Customer " + cid + " under Salesman " + sid + ": " + totalOrders);
        } else {
            System.out.println("No orders found for Customer " + cid + " under Salesman " + sid);
        }

        rs.close();
        stmt.close();
    }
}
