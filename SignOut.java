/**
 * Author: Ravi Tiwari
 * Filename: SignOut.java
 * Specifications: A Java file with JFrame which appears on successful SignOut
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignOut extends JFrame {

	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel signOutLabel = new JLabel();
	private JLabel signInLabel = new JLabel();

	//default constructor
	public SignOut() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
	}
	// set the look and feel of frame
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

		//SignOut frame
		SignOut frame = new SignOut();
		frame.setTitle("Casino Coffee");
		frame.setSize(600,200);
		frame.setDefaultCloseOperation(LandingPage.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}                              
	private void design() {

		//set font, foreground and Text of the components
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		signOutLabel.setFont(new Font("Arial", 0, 18)); 
		signOutLabel.setForeground(new Color(0, 102, 102));
		signOutLabel.setText("You have signed out successfully");

		signInLabel.setFont(new Font("Arial", 0, 18));
		signInLabel.setText("Sign In Now");
		signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		//adding MouseListener to signInLabel
		signInLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//change frame on click
					LandingPage f = new LandingPage();
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.setResizable(false);
					f.setTitle("Casino Coffee");
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
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(58, Short.MAX_VALUE)
						.addComponent(welcomeLabel)
						.addGap(40, 40, 40))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(158, 158, 158)
										.addComponent(signOutLabel))
								.addGroup(layout.createSequentialGroup()
										.addGap(225, 225, 225)
										.addComponent(signInLabel)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(35, 35, 35)
						.addComponent(signOutLabel)
						.addGap(18, 18, 18)
						.addComponent(signInLabel)));
		pack();
	}                   
}

