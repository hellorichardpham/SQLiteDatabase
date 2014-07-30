import java.sql.*;
import java.util.Scanner;

public class UtilizeTableSQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			c.setAutoCommit(false);
	
			stmt = c.createStatement();
	
			String userAction = promptUserAction();
			String sql = generateSQLCommand(userAction);
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			c.commit();
			stmt.close();
			c.close();
			System.out.println("Action Successful.");
		} catch(Exception e) {
			//do something
		}
	}
	
	private static String generateSQLCommand(String userAction) {
		switch(userAction) {
		case "update":
			return generateUpdate();
		
		case "select":
			return generateSelect();
			
		case "insert":
			return generateInsert();
		
		case "delete":
			return generateDelete();
		}
		return "";
	}
	private static String promptUserAction() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("What would you like to do? (Update, Select, Insert, Delete, Cancel)");
		while (true) {
			switch (scan.nextLine().toLowerCase()) {
			case "update":
				return "update";
			case "select":
				return "select";
			case "insert":
				return "insert";
			case "delete":
				return "delete";
			case "cancel":
				System.out.println("The program is now ending.");
				System.exit(0);
			default:
				System.out
						.println("That is not a valid command. Try again using one of these words: Update, Select, Add, Cancel");
			}
		}
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

		System.out.println(categoryToCheck + " " + valueToCheck + " "
				+ categoryToChange + " " + value);
		if (categoryToChange.equals("GRADE") || categoryToChange.equals("ID")) {
			return "UPDATE Grades set " + categoryToChange + "=" + value
					+ " WHERE " + categoryToCheck + "= '" + valueToCheck + "';";
		} else {
			// This case is where we are updating "Name" or "Comments" so we
			// need a ' ' around value
			return "UPDATE Grades set " + categoryToChange + "=" + "'" + value
					+ "'" + " WHERE " + categoryToCheck + "= '" + valueToCheck
					+ "';";
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
			return "DELETE from Grades WHERE " + targetCategory + "=" + "'"
					+ targetValue + "'";
		}
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
	
	public static String generateSelect() {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Please indicate the name of the student that you would like to access.");
		String name = scan.nextLine();
		return "SELECT * FROM Grades WHERE NAME = '" + name + "';";
	}
}
