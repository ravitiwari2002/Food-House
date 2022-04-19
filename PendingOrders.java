/**
 * Filename: PendingOrders.java
 * Specifications: A Java file with JFrame which allows Manager and Employee to check Pending Orders
 * For: CSE 205 - Final Project
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PendingOrders extends JFrame {

	private JLabel welcomeLabel = new JLabel();
	private JLabel pendingLabel = new JLabel();
	private JButton clearButton = new JButton();
	private JScrollPane scroll = new JScrollPane();
	private JList idList = new JList<>();
	private JLabel idLabel = new JLabel();
	private JButton backButton = new JButton();

	static Connection c = null;
	static Statement stmt = null;

	public PendingOrders() {
		design();
	}

	public static void main(String args[]) {
		databaseConnection();
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
			System.out.println("Nimbus not available");
		}
		PendingOrders frame = new PendingOrders();
		frame.setTitle("Casino Coffee");
		frame.setDefaultCloseOperation(PendingOrders.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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

	private void signOut() {
		try {                       //Signout button logic
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			stmt = c.createStatement();        //grabs username of person signed in from database
			ResultSet rs = stmt.executeQuery("select username FROM employees where status = 'SIGNED IN';"); 
			String username= "";
			while(rs.next()) {
				username = rs.getString("USERNAME");
			}
			try {
				c.setAutoCommit(false);
				stmt = c.createStatement(); //updates status from grabbed username to signed out
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
                    
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		pendingLabel.setFont(new Font("Arial", 0, 24)); 
		pendingLabel.setForeground(new Color(0, 102, 102));
		pendingLabel.setText("Pending Orders");

		idList.setBackground(new Color(204, 204, 255));
		idList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 0, 0)));
		idList.setFont(new Font("Algerian", 1, 18)); 
		idList.setForeground(new Color(204, 0, 51));

		DefaultListModel dlm = new DefaultListModel();
		
		//get orderId from database and add them to the JList
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * FROM orders where Status = 'PENDING';");
						String orderid = "";
			String orderTime = "";

			while(rs.next()) {
				
				orderid = rs.getString("ORDERID");
				orderTime = rs.getString("ORDEREDAT");
								
				dlm.addElement(orderid + "            " + orderTime);
				idList.setModel(dlm);
			}			
		}
		catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
			System.exit(0);
		}	

		scroll.setViewportView(idList);

		clearButton.setFont(new Font("Arial", 1, 14)); 
		clearButton.setText("Clear Order");

		clearButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				if(!(idList.isSelectionEmpty())) {
					String orderId = idList.getSelectedValue().toString().substring(0,6);	

					try {

						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");

						stmt = c.createStatement();
						c.setAutoCommit(false);   // clear order logic
						String sql = "UPDATE ORDERS set STATUS = 'CLEARED' where ORDERID = '" + orderId + "'";
						stmt.executeUpdate(sql);
						c.commit();

						dlm.removeElementAt(idList.getSelectedIndex());


					}
					catch (Exception e1) {
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}	
				}
			}
		});

		idLabel.setFont(new Font("Arial", 1, 16)); 
		idLabel.setText("Select an Order");

		backButton.setFont(new Font("Arial", 1, 14)); 
		backButton.setText("Back");
		backButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				EmployeeLogIn f = new EmployeeLogIn();
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(EmployeeLogIn.EXIT_ON_CLOSE);
				f.setResizable(false);
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

            //JFrame logic
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(209, 209, 209)
										.addComponent(pendingLabel))
								.addGroup(layout.createSequentialGroup()
										.addGap(91, 91, 91)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE)
												.addComponent(idLabel))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(58, Short.MAX_VALUE)
						.addComponent(welcomeLabel)
						.addGap(40, 40, 40))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(129, 129, 129)
						.addComponent(backButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(clearButton)
						.addGap(109, 109, 109)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(36, 36, 36)
						.addComponent(pendingLabel)
						.addGap(13, 13, 13)
						.addComponent(idLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(clearButton)
								.addComponent(backButton))
						.addContainerGap(124, Short.MAX_VALUE)));

		pack();
	}                                    
}

