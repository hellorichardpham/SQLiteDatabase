import java.sql.*;
public class CreateTableSQLite {
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
			System.out.println("Database successfully opened.");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE Grades "
					+ "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, "
					+ " GRADE           INT     NOT NULL, "
					+ " COMMENTS        TEXT)";
			
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}
