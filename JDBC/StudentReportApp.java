import java.sql.*;
import java.util.Scanner;

public class StudentReportApp{
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
                System.out.println("1. Insert Student");
                System.out.println("2. Insert Marks");
                System.out.println("3. Update Student");
                System.out.println("4. Update Marks");
                System.out.println("5. Delete Student");
                System.out.println("6. Delete Marks");
                System.out.println("7. Display Student Report");
                System.out.println("8. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        insertStudent(con, scanner);
                        break;
                    case 2:
                        insertMarks(con, scanner);
                        break;
                    case 3:
                        updateStudent(con, scanner);
                        break;
                    case 4:
                        updateMarks(con, scanner);
                        break;
                    case 5:
                        deleteStudent(con, scanner);
                        break;
                    case 6:
                        deleteMarks(con, scanner);
                        break;
                    case 7:
                        displayReport(con);
                        break;
                    case 8:
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

    private static void insertStudent(Connection con, Scanner scanner) throws SQLException {
        String query = "INSERT INTO Student (student_id, student_name, course_name) VALUES (?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();

        stmt.setInt(1, studentId);
        stmt.setString(2, studentName);
        stmt.setString(3, courseName);

        int rowsInserted = stmt.executeUpdate();
        System.out.println(rowsInserted + " records inserted");

        stmt.close();
    }

    private static void insertMarks(Connection con, Scanner scanner) throws SQLException {
        String query = "INSERT INTO Marks (student_id, score) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter Score: ");
        int score = scanner.nextInt();

        stmt.setInt(1, studentId);
        stmt.setInt(2, score);

        int rowsInserted = stmt.executeUpdate();
        System.out.println(rowsInserted + " records inserted");

        stmt.close();
    }

    private static void updateStudent(Connection con, Scanner scanner) throws SQLException {
        String query = "UPDATE Student SET student_name = ?, course_name = ? WHERE student_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Student Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter new Course Name: ");
        String courseName = scanner.nextLine();

        stmt.setString(1, studentName);
        stmt.setString(2, courseName);
        stmt.setInt(3, studentId);

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated + " records updated");

        stmt.close();
    }

    private static void updateMarks(Connection con, Scanner scanner) throws SQLException {
        String query = "UPDATE Marks SET score = ? WHERE student_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID to update marks: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter new Score: ");
        int score = scanner.nextInt();

        stmt.setInt(1, score);
        stmt.setInt(2, studentId);

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated + " records updated");

        stmt.close();
    }

    private static void deleteStudent(Connection con, Scanner scanner) throws SQLException {
        String query = "DELETE FROM Student WHERE student_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();

        stmt.setInt(1, studentId);

        int rowsDeleted = stmt.executeUpdate();
        System.out.println(rowsDeleted + " records deleted");

        stmt.close();
    }

    private static void deleteMarks(Connection con, Scanner scanner) throws SQLException {
        String query = "DELETE FROM Marks WHERE student_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        System.out.print("Enter Student ID to delete marks: ");
        int studentId = scanner.nextInt();

        stmt.setInt(1, studentId);

        int rowsDeleted = stmt.executeUpdate();
        System.out.println(rowsDeleted + " records deleted");

        stmt.close();
    }

    private static void displayReport(Connection con) throws SQLException {
        String query = "SELECT s.student_id, s.student_name, s.course_name, m.score " +
                       "FROM Student s INNER JOIN Marks m ON s.student_id = m.student_id " +
                       "ORDER BY s.course_name";
        
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Student Report:");
        System.out.printf("%-10s %-20s %-20s %-10s %-10s%n", "ID", "Name", "Course", "Score", "Grade");

        while (rs.next()) {
            int id = rs.getInt("student_id");
            String name = rs.getString("student_name");
            String course = rs.getString("course_name");
            int score = rs.getInt("score");

            String grade = calculateGrade(score);

            System.out.printf("%-10d %-20s %-20s %-10d %-10s%n", id, name, course, score, grade);
        }

        rs.close();
        stmt.close();
    }

    private static String calculateGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
}
