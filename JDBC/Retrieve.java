import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Retrieve{
    public static void main(String args[]) {
        try {
            // Establishing database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");
            System.out.println("Connection established to Database");

            // Prepared statement for retrieving customer data
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT customer_id, name, address, status " +
                            "FROM Customer1 " +
                            "WHERE customer_id = ?");

            // Taking input from user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Retrieve Customer Data");
            System.out.print("Enter Customer ID: ");
            int customerID = scanner.nextInt();
            stmt.setInt(1, customerID);

            // Executing the query and retrieving the result set
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int retrievedCustomerID = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String status = resultSet.getString("status");

                // Displaying retrieved data
                System.out.println("Customer ID: " + retrievedCustomerID);
                System.out.println("Name: " + name);
                System.out.println("Address: " + address);
                System.out.println("Status: " + status);
            } else {
                System.out.println("Customer not found for Customer ID: " + customerID);
            }

            // Closing resources
            resultSet.close();
            stmt.close();
            con.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
