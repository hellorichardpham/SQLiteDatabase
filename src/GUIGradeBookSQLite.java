import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class GUIGradeBookSQLite {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel nameLabel = new JLabel("Name:");
		JLabel idLabel = new JLabel("ID:");
		JLabel gradeLabel = new JLabel("Grade:");

		JTextField nameField = new JTextField(20);
		JTextField idField = new JTextField(5);
		JTextField gradeField = new JTextField(5);

		JTextArea resultArea = new JTextArea(3, 10);
		resultArea.setBorder(border);

		resultArea.setWrapStyleWord(true);
		resultArea.setLineWrap(true);
		JButton insertButton = new JButton("Insert");
		JButton deleteButton = new JButton("Delete");
		JButton selectButton = new JButton("Select");
		JButton updateButton = new JButton("Update");
		Listens listenObject = new Listens(nameField, idField, gradeField,
				insertButton, deleteButton);
		insertButton.addActionListener(listenObject);
		deleteButton.addActionListener(listenObject);
		selectButton.addActionListener(listenObject);
		updateButton.addActionListener(listenObject);

		Panel panel = new Panel();
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(idLabel);
		panel.add(idField);
		panel.add(gradeLabel);
		panel.add(gradeField);
		panel.add(insertButton);
		panel.add(deleteButton);
		panel.add(resultArea);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
