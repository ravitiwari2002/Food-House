/**
 * Author: Ravi Tiwari
 * Filename: SignUpChoice.java
 * Specifications: A Java file with JFrame which allows users to choose between Customers And Employees during SignUp
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignUpChoice extends JFrame {

	//instance variables
	private JRadioButton customerRb = new JRadioButton();
	private JRadioButton employeeRb = new JRadioButton();
	private JLabel signUpLabel = new JLabel();
	private JLabel welcomeLabel = new JLabel();

	//default constructor
	public SignUpChoice() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
	}

	// set look and feel of frame and make frame visible
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

		SignUpChoice frame = new SignUpChoice();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(SignUpChoice.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}
	private void design() {

		//Action Listener for customerRb Radio Button
		customerRb.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//change frame
				SignUpPageForCustomers f = new SignUpPageForCustomers();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(SignUpPageForCustomers.EXIT_ON_CLOSE);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});

		//Action Listener for employeeRb Radio Button
		employeeRb.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//change frame
				SignUpPageForEmployees f1 = new SignUpPageForEmployees();
				f1.setResizable(false);
				f1.setTitle("Casino Coffee");
				f1.setDefaultCloseOperation(SignUpPageForEmployees.EXIT_ON_CLOSE);
				f1.setLocationRelativeTo(null);
				f1.setVisible(true);
				dispose();
			}
		});

		//add welcomeLabel
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		//add signUpLabel
		signUpLabel.setFont(new Font("Arial", 1, 18)); 
		signUpLabel.setText("Sign Up");

		//add customerRb Radio Button
		customerRb.setFont(new Font("Arial", 0, 14)); 
		customerRb.setText("Customer");

		//add employeeRb Employee Button
		employeeRb.setFont(new Font("Arial", 0, 14));
		employeeRb.setText("Employee");

		//Using GroupLayout to add Components 
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(105, 105, 105)
										.addComponent(customerRb)
										.addGap(137, 137, 137)
										.addComponent(employeeRb))
								.addGroup(layout.createSequentialGroup()
										.addGap(229, 229, 229)
										.addComponent(signUpLabel))
								.addGroup(layout.createSequentialGroup()
										.addGap(26, 26, 26)
										.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(26, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(31, 31, 31)
						.addComponent(signUpLabel)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(customerRb)
								.addComponent(employeeRb))
						.addContainerGap(64, Short.MAX_VALUE)));
		pack();
	}                      
}
