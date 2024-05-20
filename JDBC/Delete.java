import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete{
    public static void main(String args[]) {
        try {
            // Establishing database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dksql");
            System.out.println("Connection established to Database");

            // Prepared statement for deleting records based on status
            PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM Customer1 WHERE status = ?");

            // Taking input from user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Delete Customer Data by Status");
            System.out.print("Enter Status (Paid/Unpaid): ");
            String status = scanner.nextLine();

            stmt.setString(1, status);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println(rowsDeleted + " record(s) deleted for Status: " + status);
            } else {
                System.out.println("No records found for Status: " + status);
            }

            // Closing resources
            stmt.close();
            con.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
