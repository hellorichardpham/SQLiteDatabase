import java.sql.*;
import java.util.Scanner;

public class SelectTableSQLite {
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			System.out.println("Database opened successfully");
			ResultSet rs = stmt.executeQuery(generateSelectCommand());
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String name = rs.getString("NAME");
				int grade = rs.getInt("GRADE");
				String comments = rs.getString("COMMENTS");
				printStudentInfo(ID, name, grade, comments);
			}
		} catch (Exception e) {
		}
	}

	public static String generateSelectCommand() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Please indicate the name of the student that you would like to access.");
		String name = scan.nextLine();
		return "SELECT * FROM Grades WHERE NAME = '" + name + "';";
	}

	public static void printStudentInfo(int ID, String name, int grade,
			String comments) {
		System.out.printf("ID: %d \nName: %s \nGrade: %d \nComments: %s \n",
				ID, name, grade, comments);
	}
}
