/**
 * Author: Ravi Tiwari
 * Filename: EmployeeAddedCustomerMessage.java
 * Specifications: A Java file with JFrame which appears when Employee Add Customers successfully
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeeAddedCustomerMessage extends JFrame {
	private JLabel welcomeLabel = new JLabel();
	private JLabel  msgLabel = new JLabel();
	private JButton homeButton = new JButton();

	public EmployeeAddedCustomerMessage() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
	}

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

		EmployeeAddedCustomerMessage frame = new EmployeeAddedCustomerMessage();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EmployeeAddedCustomerMessage.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}

	private void design() {

		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		msgLabel.setFont(new Font("Arial", 0, 24)); 
		msgLabel.setForeground(new Color(0, 102, 102));
		msgLabel.setText("Customer added Succesfully");

		homeButton.setFont(new Font("Arial", 1, 14));
		homeButton.setText("Go To Home");

		homeButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				EmployeeLogIn f = new EmployeeLogIn();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(EmployeeLogIn.EXIT_ON_CLOSE);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});

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
