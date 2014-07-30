import java.sql.*;
import java.util.Scanner;

public class DeleteTableSQLite {

	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = generateDelete();
			stmt.executeUpdate(sql);
			c.commit();
			c.close();
			stmt.close();
			System.out.println("Success");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

	public static String generateDelete() {
		Scanner scan = new Scanner(System.in);
		System.out.println("What category would you like to delete from?");
		String targetCategory = scan.nextLine().toLowerCase();
		System.out.println("What value of " + targetCategory
				+ " would you like to delete?");
		String targetValue = scan.nextLine().toLowerCase();
		if (targetCategory.equals("grades") || targetCategory.equals("id")) {
			return "DELETE from Grades WHERE " + targetCategory + "="
					+ targetValue;
		} else {
			return "DELETE from Grades WHERE " + targetCategory + "="
					+ "'" +targetValue + "'";
		}
	}
}
