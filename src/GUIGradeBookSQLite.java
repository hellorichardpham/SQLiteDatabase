import javax.swing.*;

import java.awt.*;

public class GUIGradeBookSQLite {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel nameLabel = new JLabel("Name:");
		JLabel idLabel = new JLabel("ID:");
		JLabel gradeLabel = new JLabel("Grade:");

		JTextField nameField = new JTextField(20);
		JTextField idField = new JTextField(5);
		JTextField gradeField = new JTextField(5);
		
		JButton insertButton = new JButton("Insert");
		JButton deleteButton = new JButton("Delete");
		
		Listens listenObject = new Listens(nameField, idField, gradeField, insertButton, deleteButton);		
		insertButton.addActionListener(listenObject);
		deleteButton.addActionListener(listenObject);
		Panel panel = new Panel();
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(idLabel);
		panel.add(idField);
		panel.add(gradeLabel);
		panel.add(gradeField);
		panel.add(insertButton);
		panel.add(deleteButton);
		frame.add(panel);
		frame.setSize(700, 150);
		frame.setVisible(true);
	}

}
