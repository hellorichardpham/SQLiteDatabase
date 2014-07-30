import java.sql.*;
import java.util.Scanner;

public class UpdateTableSQLite {

	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			c.setAutoCommit(false);
			System.out.println("Successfully connected to database.");
				stmt = c.createStatement();
				String sql = generateUpdate();
				stmt.executeUpdate(sql);
				c.commit();
				System.out.println("Update successful.");
			c.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private static boolean promptContinueUpdate() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Would you like to continue? (Type \"Yes\" or \"No\")");
		if (scan.nextLine().toLowerCase().equals("yes")) {
			return true;
		}
		return false;
	}

	private static String generateUpdate() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Please specify whether you would like to update based on \"ID\" or \"NAME\"");
		String categoryToCheck = scan.nextLine().toUpperCase();
		System.out.println("Please input the value for that category.");
		String valueToCheck = scan.nextLine();
		System.out
				.println("Please input the category that you wish to change.");
		String categoryToChange = scan.nextLine().toUpperCase();
		System.out.println("Please input the new value for that category.");
		String value = scan.nextLine();
		
		System.out.println(categoryToCheck + " " + valueToCheck + " " + categoryToChange + " " + value);
		if (categoryToChange.equals("GRADE") || categoryToChange.equals("ID")) {
			return "UPDATE Grades set " + categoryToChange + "=" + value
					+ " WHERE " + categoryToCheck + "= '" + valueToCheck + "';";
		} else {
			//This case is where we are updating "Name" or "Comments" so we need a ' ' around value
			return "UPDATE Grades set " + categoryToChange + "=" + "'" +value +"'" 
					+ " WHERE " + categoryToCheck + "= '" + valueToCheck + "';";
		}
	}
}
