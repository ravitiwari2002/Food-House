/**
 * Author: Ravi Tiwari
 * Filename: SuccessfulSignUp.java
 * Specifications: A Java file with JFrame which appears with a message on successful signUp
 * For: CSE 205 - Final Project
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SuccessfulSignUp extends JFrame {

	//instance variables
	private JLabel msgLabel = new JLabel();
	private JLabel signInLabel = new JLabel();
	private JLabel welcomeLabel = new JLabel();

	//default constructor
	public SuccessfulSignUp() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
	}

	//set look and feel of frame and set it visible
	private static void viewFrame() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus not available");
		}
		SuccessfulSignUp frame = new SuccessfulSignUp();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(SuccessfulSignUp.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}

	private void design() {

		//set font, foreground, and text of the components
		welcomeLabel.setFont(new Font("Harrington", 1, 36));
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		msgLabel.setFont(new Font("Arial", 0, 18));
		msgLabel.setForeground(new Color(153, 51, 255));
		msgLabel.setText("You have signed up successfully.");

		signInLabel.setFont(new Font("Arial", 1, 18));
		signInLabel.setText("Sign In Now");
		signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		//Add MouseListener to the signInLabel
		signInLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					LandingPage f = new LandingPage();
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(LandingPage.EXIT_ON_CLOSE);
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signInLabel.setText("Sign In Now");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				signInLabel.setText("<html><a href=''>" + "Sign In Now" + "</a></html>");
			}
		});

		//using GroupLayout to add components to the frame
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(69, Short.MAX_VALUE)
						.addComponent(
								welcomeLabel, GroupLayout.PREFERRED_SIZE, 513,
								GroupLayout.PREFERRED_SIZE)
						.addGap(55, 55, 55))
				.addGroup(layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(183, 183, 183).addComponent(msgLabel))
						.addGroup(
								layout.createSequentialGroup().addGap(249, 249, 249).addComponent(signInLabel)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(welcomeLabel).addGap(30, 30, 30)
						.addComponent(msgLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(signInLabel).addContainerGap(34, Short.MAX_VALUE)));

		pack();
	}



}
