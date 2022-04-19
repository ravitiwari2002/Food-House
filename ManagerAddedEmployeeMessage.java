/**
 * Author: Ravi Tiwari
 * Filename: ManagerAddedEmployeesMessage.java
 * Specifications: A Java file with JFrame which appears as message when manager add employee succesfully
 * For: CSE 205 - Final Project
  */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerAddedEmployeeMessage extends JFrame {
	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel  msgLabel = new JLabel();
	private JButton homeButton = new JButton();

	//default constructor
	public ManagerAddedEmployeeMessage() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
	}

	//set look and feel of the frame and set it visible
	private static void viewFrame() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus not found");
		}

		ManagerAddedEmployeeMessage frame = new ManagerAddedEmployeeMessage();
		frame.setTitle("Casino Coffee");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(ManagerAddedEmployeeMessage.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	private void design() {
		//set font, foreground and text of the components
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		msgLabel.setFont(new Font("Arial", 0, 24)); 
		msgLabel.setForeground(new Color(0, 102, 102));
		msgLabel.setText("Employee added Succesfully");

		homeButton.setFont(new Font("Arial", 1, 14));
		homeButton.setText("Go To Home");

		homeButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//change frame when button is clicked
				ManagerLogIn f = new ManagerLogIn();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setVisible(true);
				dispose();
			}
		});

		//use GroupLayout to add components to the frame
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap(58, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(welcomeLabel)
										.addGap(40, 40, 40))
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(msgLabel)
										.addGap(145, 145, 145))))
				.addGroup(layout.createSequentialGroup()
						.addGap(234, 234, 234)
						.addComponent(homeButton)
						.addGap(0, 0, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(47, 47, 47)
						.addComponent(msgLabel)
						.addGap(18, 18, 18)
						.addComponent(homeButton)
						.addContainerGap(55, Short.MAX_VALUE)));
		pack();
	}
}
