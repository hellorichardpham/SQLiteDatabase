import java.sql.*;
import java.util.Scanner;

public class AddTableSQLite {

	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			boolean continueAdd = true;
			while (continueAdd) {
				String sql = generateInsert();
				stmt.executeUpdate(sql);
				continueAdd = promptContinue();
			}
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Database successfully updated");
	}

	private static boolean promptContinue() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Would you like to add another student to the gradebook?");
		if (scan.nextLine().toLowerCase().equals("yes")) {
			return true;
		}
		return false;
	}

	public static String generateInsert() {
		Scanner scanNumbers = new Scanner(System.in);
		Scanner scanStrings = new Scanner(System.in);

		System.out.println("Please input an ID.");
		int ID = scanNumbers.nextInt();
		System.out.println("Please input a name.");
		String name = scanStrings.nextLine().toLowerCase();
		System.out.println("Please input a grade.");
		int grade = scanNumbers.nextInt();
		System.out.println("Please input any comments about " + name
				+ ". Type \"None\" if there are none");
		String comments = scanStrings.nextLine().toLowerCase();

		return "INSERT INTO GRADES(ID,NAME,GRADE,COMMENTS)" + "VALUES (" + ID
				+ ", " + "'" + name + "', " + grade + ", '" + comments + "' );";

	}

}
