import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Update{
    public static void main(String args[]) {
        try {
           // Establishing database connection
Class.forName("oracle.jdbc.driver.OracleDriver");
Connection con = DriverManager.getConnection(
    "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");
System.out.println("Connection established to Database");


            PreparedStatement stmt = con.prepareStatement("UPDATE Customer1 SET name=?, address=?, contact_number=?, status=? WHERE customer_id=?");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Update Customer Record");
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

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, contactNumber);
            stmt.setString(4, status);
            stmt.setInt(5, customerID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(rowsUpdated + " records updated");
            } else {
                System.out.println("No records updated. Customer ID not found.");
            }

            stmt.close();
            con.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
