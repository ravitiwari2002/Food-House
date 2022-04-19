/**
 * Author: Ravi Tiwari
 * Filename: ModifyItems.java
 * Specifications: A Java file with JFrame which allows employees to Modify Items
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ModifyItems extends JFrame {

	private JLabel welcomeLabel = new JLabel();
	private JList  itemsAvailableList = new JList<>();
	private JScrollPane scroll = new JScrollPane();
	private JLabel itemsInCartLabel = new JLabel();
	private JButton addCustomerButton = new JButton();
	private JButton pOrderButton = new JButton();
	private JButton previousPage = new JButton();
	private JButton signOutButton = new JButton();
	private JLabel itemNameLabel = new JLabel();
	private JLabel priceLabel = new JLabel();
	private JTextField itemNameTF = new JTextField();
	private JTextField priceTF = new JTextField();
	private JButton addnewItemButton = new JButton();
	private JButton dltItemButton = new JButton();
	private JButton updateButton = new JButton();
	private JLabel modifyHeaderLabel = new JLabel();
	private JLabel verLabel = new JLabel();

	static Connection c = null;
	static Statement stmt = null;

	static ArrayList <String> items = new ArrayList <String>();

	public ModifyItems() {
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from item;");

			while(rs.next()) {
				String itemName = rs.getString("ITEMNAME");				//get items from database
				items.add(itemName);
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
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
		ModifyItems frame = new ModifyItems();
		frame.setTitle("Casino Coffee");
		frame.setDefaultCloseOperation(ModifyItems.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//connecting with a database
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

	//signOut
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

		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		itemsAvailableList.setBackground(new Color(204, 204, 255));
		itemsAvailableList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 0, 0)));
		itemsAvailableList.setFont(new Font("Algerian", 1, 18)); 
		itemsAvailableList.setForeground(new Color(204, 0, 51));

		itemsAvailableList.setSelectionBackground(new Color(204, 153, 255));
		scroll.setViewportView(itemsAvailableList);
		DefaultListModel dlm = new DefaultListModel();
		for (int i = 0; i < Items.itemsArray.size(); i++)
		{
			dlm.addElement(Items.itemsArray.get(i).getName());
		}
		itemsAvailableList.setModel(dlm);


		itemsInCartLabel.setFont(new Font("Arial", 1, 24)); 
		itemsInCartLabel.setText("Current Item List");

		addCustomerButton.setFont(new Font("Arial", 1, 14)); 
		addCustomerButton.setText("Add a Customer");
		addCustomerButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				try {
					EmployeeAddCustomer f = new EmployeeAddCustomer();
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.setDefaultCloseOperation(EmployeeAddCustomer.EXIT_ON_CLOSE);
					f.addWindowListener(new WindowAdapter()
					{
						@Override
						public void windowClosing(WindowEvent e)
						{
							super.windowClosing(e);
							signOut();
						}
					});
					dispose();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		pOrderButton.setFont(new Font("Arial", 1, 14)); 
		pOrderButton.setText("Pending Orders");
		pOrderButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				try {
					PendingOrders f = new PendingOrders();
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(PendingOrders.EXIT_ON_CLOSE);
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.addWindowListener(new WindowAdapter()
					{
						@Override
						public void windowClosing(WindowEvent e)
						{
							super.windowClosing(e);
							signOut();		
						}
					});
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		previousPage.setFont(new Font("Arial", 1, 14)); 
		previousPage.setText("Back");
		previousPage.addActionListener (new ActionListener () {
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

		signOutButton.setFont(new Font("Arial", 1, 14)); 
		signOutButton.setText("Sign Out");
		signOutButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//change status of Employee to SignedOut in database and then change frame.
				signOut();

				try {
					SignOut f = new SignOut();
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(SignOut.EXIT_ON_CLOSE);
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.addWindowListener(new WindowAdapter()
					{
						@Override
						public void windowClosing(WindowEvent e)
						{
							super.windowClosing(e);
							signOut();		
						}
					});
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		itemNameLabel.setFont(new Font("Arial", 1, 14)); 
		itemNameLabel.setText("Item Name");

		priceLabel.setFont(new Font("Arial", 1, 14)); 
		priceLabel.setText("Price");

		addnewItemButton.setFont(new Font("Arial", 1, 14)); 
		addnewItemButton.setText("Add Item to the List");

		addnewItemButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
		
				Items newItem = new Items(itemNameTF.getText(), Double.parseDouble(priceTF.getText()), 100);

				if (itemNameTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Enter the Item Name ", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (priceTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Enter the Price ", "" + "Message", JOptionPane.INFORMATION_MESSAGE);	
				}
				else {
					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						c.setAutoCommit(false);
						stmt = c.createStatement();
						String sql = "INSERT INTO ITEM (" + "ITEMNAME, PRICE, QUANTITY)" 
								+ "VALUES('"+ newItem.getName() +"','" + newItem.getPrice() +"' ,'" + newItem.getAvailableQuant() + "');";
						stmt.executeLargeUpdate(sql);

						JOptionPane.showMessageDialog(null, "New Item Added", "" + "Message", JOptionPane.INFORMATION_MESSAGE);

						itemNameTF.setText("");
						priceTF.setText("");
						DefaultListModel dlm = new DefaultListModel();
						Items.itemsArray.add(newItem);
						for (int i = 0; i < Items.itemsArray.size(); i++)
						{
							dlm.addElement(Items.itemsArray.get(i).getName());
						}
						itemsAvailableList.setModel(dlm);

						c.commit();
						c.close();
						stmt.close();
					}

					catch (Exception e1){
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}
				}
			}
		});

		verLabel.setFont(new Font("Arial", 1, 18)); 
		verLabel.setForeground(new Color(0, 153, 153));
		verLabel.setText("Version 0.1.0");

		dltItemButton.setFont(new Font("Arial", 1, 14)); 
		dltItemButton.setText("Delete Item from the List ");

		// delete existing item from the database
		dltItemButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if(itemsAvailableList.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Select an Item to Delete", "" + "Message", JOptionPane.INFORMATION_MESSAGE);	
				}
				else {
					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						c.setAutoCommit(false);
						stmt = c.createStatement();
						String selectedItem = itemsAvailableList.getSelectedValue().toString();
						String sql = "DELETE FROM ITEM where itemname = '"+ selectedItem + "';";

						DefaultListModel dlm = new DefaultListModel();

						int indexOfItemToRemove = -1;
						for (int i = 0; i < Items.itemsArray.size(); i++){
							if (Items.itemsArray.get(i).getName().equals(selectedItem)){
								indexOfItemToRemove = i;
							}
						}
						Items.itemsArray.remove(indexOfItemToRemove);

						for (int i = 0; i < Items.itemsArray.size(); i++)
						{
							dlm.addElement(Items.itemsArray.get(i).getName());
						}
						itemsAvailableList.setModel(dlm);

						stmt.execute(sql);
						c.commit();
						stmt.close();
						c.close();
					}
					catch (Exception e1){
						e1.printStackTrace();
						System.err.println(e.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}
				}
			}

		});

		updateButton.setFont(new Font("Arial", 1, 14)); 
		updateButton.setText("Update Item in the List");

		updateButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if(itemsAvailableList.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Select an Item to Update", "" + "Message", JOptionPane.INFORMATION_MESSAGE);	
				}
				else {
					String selectedItem = itemsAvailableList.getSelectedValue().toString();
					String updatedPrice = JOptionPane.showInputDialog(null, "Update Price", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					String availability = JOptionPane.showInputDialog(null, "Available Quantity", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						c.setAutoCommit(false);
						stmt = c.createStatement();
						for (Items i : Items.itemsArray){
							if (i.getName().equals(selectedItem)){
								i.setPrice(Double.parseDouble(updatedPrice));
								i.setAvailableQuant(Integer.parseInt(availability));
							}
						}

						String sql = "UPDATE ITEM set PRICE = '" + updatedPrice +"' where itemname = '"+ selectedItem + "';";
						String sql1 = "UPDATE ITEM set QUANTITY = '" + availability +"' where itemname = '"+ selectedItem + "';";

						stmt.execute(sql);
						stmt.execute(sql1);
						c.commit();
						stmt.close();
						c.close();
					}
					catch (Exception e1){
						e1.printStackTrace();
						System.err.println(e.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}
				}
			}
		});

		modifyHeaderLabel.setFont(new Font("Arial", 1, 18)); 
		modifyHeaderLabel.setForeground(new Color(0, 102, 102));
		modifyHeaderLabel.setText("Modify Item List");

		//Group layout for the GUI
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(addCustomerButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(itemsInCartLabel,GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent(pOrderButton)
																.addComponent(previousPage, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
														.addGap(185, 185, 185)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																.addComponent(itemNameTF, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
																.addComponent(priceTF))
														.addGap(0, 0, Short.MAX_VALUE))))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(197, 197, 197)
																		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																				.addComponent(itemNameLabel)
																				.addComponent(priceLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
																.addGroup(layout.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(signOutButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
																		.addGap(90, 90, 90)
																		.addComponent(addnewItemButton, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(layout.createSequentialGroup()
														.addContainerGap()
														.addComponent(dltItemButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
														.addComponent(updateButton)
														.addGap(37, 37, 37)))
										.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(37, 37, 37)
										.addComponent(verLabel))
								.addGroup(layout.createSequentialGroup()
										.addGap(278, 278, 278)
										.addComponent(modifyHeaderLabel)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
						.addGap(107, 107, 107))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(48, 48, 48)
										.addComponent(modifyHeaderLabel)
										.addGap(15, 15, 15)
										.addComponent(addCustomerButton)
										.addGap(9, 9, 9))
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(itemsInCartLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(pOrderButton)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(itemNameLabel)
														.addComponent(itemNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(priceLabel)
																.addComponent(priceTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGap(18, 18, 18)
														.addComponent(addnewItemButton))
												.addGroup(layout.createSequentialGroup()
														.addGap(11, 11, 11)
														.addComponent(previousPage)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(signOutButton)))
										.addGap(86, 86, 86)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(dltItemButton)
												.addComponent(updateButton))
										.addGap(0, 0, Short.MAX_VALUE)))
						.addGap(20, 20, 20)
						.addComponent(verLabel)
						.addGap(33, 33, 33)));
		pack();
	}
}
