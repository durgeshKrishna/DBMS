import java.sql.*;
import java.util.Scanner;

public class Insert {
    public static void main(String args[]) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establishing connection to the Oracle database
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");

            System.out.println("Connection established to Database");

            // Creating a PreparedStatement for insertion
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Customer1 (customer_id, name, address, contact_number, status) VALUES (?, ?, ?, ?, ?)");

            // Taking input from user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome for Registration");
            System.out.print("Enter Customer ID: ");
            int customerID = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.nextLine();
            System.out.print("Enter Status (Paid/Unpaid): ");
            String status = scanner.nextLine();

            // Setting parameters for the PreparedStatement
            stmt.setInt(1, customerID);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setString(4, contactNumber);
            stmt.setString(5, status);

            // Executing the insertion query
            int rowsInserted = stmt.executeUpdate();
            System.out.println(rowsInserted + " records inserted");

            // Closing resources
            stmt.close();
            con.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
