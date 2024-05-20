import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class   NInsert{
    public static void main(String args[]) {
        try {
            // Establishing database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");
            System.out.println("Connection established to Database");

            // PreparedStatement for inserting data into the Customer1 table
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Customer1 (customer_id, name, address, contact_number, status) VALUES (?, ?, ?, ?, ?)");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Insert Customer Data");
            boolean continueInsertion = true;

            while (continueInsertion) {
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

                stmt.setInt(1, customerID);
                stmt.setString(2, name);
                stmt.setString(3, address);
                stmt.setString(4, contactNumber);
                stmt.setString(5, status);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println(rowsInserted + " record(s) inserted");

                    System.out.print("Do you want to insert another record? (y/n): ");
                    String response = scanner.nextLine().trim();
                    if (!response.equalsIgnoreCase("y")) {
                        continueInsertion = false;
                    }
                } else {
                    System.out.println("Insertion failed. Please try again.");
                }
            }

            stmt.close();
            con.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
