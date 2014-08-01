import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Listens implements ActionListener {
	JTextField name, ID, grade;
	/*
	 * I have a performActionButton instead of a Insert,Delete,Update button
	 * because I figure each listener should only listen to one button at a
	 * time.public Listens() { }
	 */
	JButton insertButton, deleteButton;
	Connection c = null;
	Statement stmt = null;

	Listens(JTextField name, JTextField ID, JTextField grade,
			JButton insertButton, JButton deleteButton) {
		this.name = name;
		this.ID = ID;
		this.grade = grade;
		this.insertButton = insertButton;
		this.deleteButton = deleteButton;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String sql = null;
		if (e.getSource() == insertButton) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				System.out.println("Successfully connected");
			} catch (Exception e1) {
				System.err.println(e1.getClass().getName() + ": "
						+ e1.getMessage());
			}
			sql = "INSERT into Grades (ID,NAME,GRADE)" + "VALUES ("
					+ ID.getText() + "," + "'" + name.getText().toLowerCase()
					+ "'" + "," + grade.getText() + ");";
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
				c.commit();
				stmt.close();
				c.close();
				System.out.println("done insert");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				System.out.println(e2.getClass().getName() + ": "
						+ e2.getMessage());
				try {
					stmt.close();
					c.close();
				} catch (Exception e3) {
					System.out.println(e3.getClass().getName() + ": "
							+ e3.getMessage());
				}
			}
		} else if (e.getSource() == deleteButton) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:gradebook.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				System.out.println("Successfully connected");
			} catch (Exception e1) {
				System.err.println(e1.getClass().getName() + ": "
						+ e1.getMessage());
				try {
					stmt.close();
					c.close();
				} catch (Exception e3) {
					System.out.println(e3.getClass().getName() + ": "
							+ e3.getMessage());
				}
			}
			if (ID.getText() == null) {
				sql = "DELETE from Grades WHERE " + "NAME= '" + name.getText()
						+ "';";
			} else {
				sql = "DELETE from Grades WHERE " + "NAME= '" + name.getText()
						+ "' AND ID=" + ID.getText() + ";";
			}
			System.out.println(sql);
			try {
				stmt.executeUpdate(sql);
				c.commit();
				stmt.close();
				c.close();
				System.out.println("done delete");
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				System.out.println(ex.getClass().getName() + ": "
						+ ex.getMessage());
				try {
					stmt.close();
					c.close();
				} catch (Exception e2) {
					System.out.println(e2.getClass().getName() + ": "
							+ e2.getMessage());
				}
			}

		}
	}
}
