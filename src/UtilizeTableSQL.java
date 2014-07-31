import java.sql.*;
import java.util.Scanner;

/*The next thing that I want to do is to generate the last ID of the table so that I can automatically 
 add it to the command 
 */
public class UtilizeTableSQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		Connection c = null;

		Statement stmt = null;
		boolean cont = true;
		while (cont) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				/*The purpose of lastID is solely for adding so we always keep track 
				 * of the last ID and can add to the next one.
				 */
				ResultSet rs = stmt.executeQuery(getLastIDNumber());
				int lastID = rs.getInt("ID");
				String userAction = promptUserAction();
				String sql = generateSQLCommand(userAction, lastID, stmt);
				stmt.executeUpdate(sql);
				c.commit();
				stmt.close();
				c.close();
				System.out
						.println("Action Successful. Would you like to continue? (Yes or No)");
				cont = determineContinue();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": "
						+ e.getMessage());
			}
		}

	}

	private static boolean determineContinue() {
		Scanner scan = new Scanner(System.in);
		while (true) {
			String decision = scan.nextLine().toLowerCase();
			switch (decision) {
			case "yes":
				return true;
			case "no":
				System.out.println("This application will now close.");
				return false;
			default:
				System.out
						.println("That is an invalid command. Please indicate \"Yes\" or \"No\".");
			}
		}
	}

	private static String generateSQLCommand(String userAction, int lastID,
			Statement stmt) throws Exception {
		switch (userAction) {
		case "update":
			return generateUpdate();

		case "select":
			return generateSelect(stmt);

		case "insert":
			return generateInsert(lastID);

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
			/* This case is where we are updating "Name" or "Comments" so we
			need a ' ' around value */
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

	public static String generateInsert(int lastID) {
		Scanner scanNumbers = new Scanner(System.in);
		Scanner scanStrings = new Scanner(System.in);

		int nextIDNumber = lastID + 1;
		System.out.println("Please input a name.");
		String name = scanStrings.nextLine().toLowerCase();
		System.out.println("Please input a grade.");
		int grade = scanNumbers.nextInt();
		System.out.println("Please input any comments about " + name
				+ ". Type \"None\" if there are none");
		String comments = scanStrings.nextLine().toLowerCase();

		return "INSERT INTO GRADES(ID,NAME,GRADE,COMMENTS)" + "VALUES ("
				+ nextIDNumber + ", " + "'" + name + "', " + grade + ", '"
				+ comments + "' );";
	}

	public static String generateSelect(Statement stmt) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Please indicate the name of the student that you would like to access.");
		String targetName = scan.nextLine();

		ResultSet rs = stmt.executeQuery("SELECT * FROM Grades WHERE NAME = '"
				+ targetName + "';");
		int ID = rs.getInt("ID");
		String name = rs.getString("NAME");
		int grade = rs.getInt("GRADE");
		String comments = rs.getString("COMMENTS");
		printStudentInfo(ID, name, grade, comments);
		return "";
	}

	public static String getLastIDNumber() {
		return "SELECT * FROM Grades ORDER BY ID DESC LIMIT 1;";
	}

	public static void printStudentInfo(int ID, String name, int grade,
			String comments) {
		System.out.printf("ID: %d \nName: %s \nGrade: %d \nComments: %s \n",
				ID, name, grade, comments);
	}
}
