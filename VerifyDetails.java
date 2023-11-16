/**
 * Author: Ravi Tiwari
 * Filename: VerifyDetails.java
 * Specifications: A Java file with JFrame which asks for specific questions if User wants to retrieve his username and password
 * For: CSE 205 - Final Project
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerifyDetails extends JFrame {

	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private  JLabel qLabel = new JLabel();
	private JLabel eLabel = new JLabel();
	private JTextField eTF = new JTextField();
	private JLabel phLabel = new JLabel();
	private JTextField phTF = new JTextField();
	private JButton verifyButton = new JButton();
	static Connection c = null;
	static Statement stmt = null;

	static String receiver = "";
	static String un = "";
	static String ps = "";

	//default constructor
	public VerifyDetails() {
		design();
	}

	public static void main(String args[]) throws Exception {
		viewFrame();
		databaseConnection();

	}

	private static void sendEmail(String recepient) throws Exception{

		Properties p = new Properties();

		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "587");

		String myEmail = "YOUR EMAIL";
		String myPass = "YOUR PASSWORD";

		Session sess= Session.getInstance(p, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail, myPass);
			}

		});

		Message msg = prepareMessage (sess, myEmail, recepient);

		Transport.send(msg);
		System.out.println("Sent");

	}
	private static Message prepareMessage(Session sess, String myEmail, String recepient) {
		try {
			Message msg = new MimeMessage (sess);
			msg.setFrom(new InternetAddress(myEmail));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			msg.setSubject("Test");
			msg.setText("Hello" + un + " " + ps);
			return msg;
		}
		catch (Exception e) {
			Logger.getLogger(VerifyDetails.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
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
			System.out.println("Nimbus not available");
		}

		VerifyDetails frame = new VerifyDetails();
		frame.setTitle("Casino Coffee");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(VerifyDetails.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}

	//make connection with the database
	private static void databaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			System.out.println("You are connected boy");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
	}


	private void design() {

		//set font, foreground and text of components
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		qLabel.setFont(new Font("Arial", 1, 18)); 
		qLabel.setText("Security Questions");

		eLabel.setFont(new Font("Arial", 1, 12)); 
		eLabel.setText("What is your email address?");

		phLabel.setFont(new Font("Arial", 1, 12)); 
		phLabel.setText("Please enter last 4 digits of your Phone Number");

		verifyButton.setFont(new Font("Arial", 1, 14)); 
		verifyButton.setText("Verify");

		//add ActionListener and KeyListener
		phTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}

				if (phTF.getText().length() >= 4 ) // limit textfield to 4 characters
					e.consume(); 
			}  
		});

		eTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (eTF.getText().length() >= 30 ) // limit textfield to 30 characters
					e.consume(); 
			}  
		});

		verifyButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				int count = 0;			//if data matches to customers count increases
				int count1 = 0;			//if data matches to employees count increases
				try {

					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery("select * from customers;");

					//check if email address and last 4 digit of phone number matches
					while(rs.next()) {
						String username = rs.getString("USERNAME");
						String password = rs.getString("PASSWORD");
						if(rs.getString("emailaddress").equals(eTF.getText())&& rs.getString("phonenumber").substring(6).equals(phTF.getText())) {

							un = username;
							ps = password;
							receiver = eTF.getText();

							sendEmail(receiver);

							JOptionPane.showMessageDialog(null,"Login details have been sent on the registered email address","Information",JOptionPane.INFORMATION_MESSAGE);
							count++;
							break;
						}
					}
				
					ResultSet rs1 = stmt.executeQuery("select * from employees;");
					while(rs1.next()) {
						String u = rs1.getString("USERNAME");
						String p = rs1.getString("PASSWORD");
						if(rs1.getString("emailaddress").equals(eTF.getText())&& rs1.getString("phonenumber").substring(6).equals(phTF.getText())) {

							un = u;
							ps = p;
							receiver = eTF.getText();

							sendEmail(receiver);

							JOptionPane.showMessageDialog(null,"Login details have been sent on the registered email address","Information",JOptionPane.INFORMATION_MESSAGE);
							count1++;
							break;
						}
					}
					stmt.close();
					c.close();
				}				
				catch (Exception e1) {
					e1.printStackTrace();
					System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
					System.exit(0);
				}

				//if field is empty
				if (eTF.getText().equals("") || phTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill all the details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				//if details do not match from any of the tables
				else if (count == 0 && count1 == 0 ){
					JOptionPane.showMessageDialog(null, "Incorrect Details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		//using GroupLayout to add components
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(75, Short.MAX_VALUE)
						.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
						.addGap(49, 49, 49))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(33, 33, 33)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(qLabel)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
																.addComponent(phLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(eLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(53, 53, 53)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																.addComponent(eTF)
																.addComponent(phTF, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))))
								.addGroup(layout.createSequentialGroup()
										.addGap(265, 265, 265)
										.addComponent(verifyButton)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(18, 18, 18)
						.addComponent(qLabel)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(eLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(eTF, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phLabel)
								.addComponent(phTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(32, 32, 32)
						.addComponent(verifyButton)
						.addContainerGap(40, Short.MAX_VALUE)));
		pack();
	}
}
