/**
 * Author: Ravi Tiwari
 * Filename: ManagerAddedCustomerMessage.java
 * Specifications: A Java file with JFrame which appears when manager adds customer succesfully
 * For: CSE 205 - Final Project
  */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManagerAddedCustomerMessage extends JFrame {
	
	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel  msgLabel = new JLabel();
	private JButton homeButton = new JButton();

	static Connection c = null;
	static Statement stmt = null;
	
	//default constructor
	public ManagerAddedCustomerMessage() {
		design();
		databaseConnection();
	}

	public static void main(String args[]) {
		viewFrame();
	}
	
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

	//set look and feel of the frame and set frame visible
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

		ManagerAddedCustomerMessage frame = new ManagerAddedCustomerMessage();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(ManagerAddedCustomerMessage.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
	
	//change status to signOut
			private void signOut() {
				try {
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery("select username FROM employees where status = 'SIGNED IN';");
					String username= "";
					while(rs.next()) {
						username = rs.getString("USERNAME");
					}
					try {
						c.setAutoCommit(false);
						stmt = c.createStatement();
						String sql = "UPDATE EMPLOYEES set STATUS = 'SIGNED OUT' where username = '"+username+"'";
						stmt.executeUpdate(sql);
						c.commit();

					}
					catch (Exception e1) {
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}				
				}
				catch (Exception e1) {
					e1.printStackTrace();
					System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
					System.exit(0);
				}		
			}

	private void design() {

		//set font, foreground, and text to the components
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
				//frame changes on clicking the button
				ManagerLogIn f = new ManagerLogIn();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(ManagerLogIn.EXIT_ON_CLOSE);
				f.addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						super.windowClosing(e);
						signOut();
					}
				});
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});

		//Using GroupLayout to add components to the frame
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
