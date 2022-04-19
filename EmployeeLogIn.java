/**
 * Author: Ravi Tiwari
 * Filename: EmployeeLogIn.java
 * Specifications: A Java file with JFrame which allows Employees to SignIn
 * For: CSE 205 - Final Project
 */


import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class EmployeeLogIn extends JFrame {

	//instance variables
	private JLabel  welcomeLabel = new JLabel();
	private JComboBox  itemsCB = new JComboBox<>();
	private JList  itemsInCartList = new JList<>();
	private JScrollPane scroll = new JScrollPane();
	private JLabel itemsInCartLabel = new JLabel();
	private JButton addCustomerButton = new JButton();
	private JButton modifyItemsButton = new JButton();
	private JButton pOrdersButton = new JButton();
	private JButton signOutButton = new JButton();
	private JLabel itemNameLabel = new JLabel();
	private JLabel quantLabel = new JLabel();
	private JTextField itemNameTF = new JTextField();
	private JTextField quantTF = new JTextField();
	private JButton addItemButton = new JButton();
	private JLabel verLabel = new JLabel();
	private JButton dltItemButton = new JButton();
	private JButton updateButton = new JButton();
	private JButton placeOrderButton = new JButton();

	static Connection c = null;
	static Statement stmt = null;
	static boolean inRange = true;

	static ArrayList <Items> itemsInCart = new ArrayList <Items> ();

	public EmployeeLogIn() {
		design();
	}

	public static void main(String args[]) {
		databaseConnection();
		viewFrame();
	}

	// set look and feel of frame and make it visible
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
		EmployeeLogIn frame = new EmployeeLogIn();
		frame.setTitle("Casino Coffee");
		frame.setDefaultCloseOperation(EmployeeLogIn.EXIT_ON_CLOSE);
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

	//get the current signedIn current User
	private String getCurrentUser() {
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			final String queryCheck = "SELECT * from employees";
			ResultSet rs = stmt.executeQuery(queryCheck);
			while(rs.next()) {
				if(rs.getString("status").equals("SIGNED IN")) {
					return rs.getString("username");
				}
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
			System.exit(0);
		}
		return null;
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
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");


		itemsCB.setFont(new Font("Algerian", 0, 14)); 
		itemsCB.addItem("Select Item");

		//add Item objects to the ComboBox
		for(Items i : Items.itemsArray) {
			itemsCB.addItem(i.toString());
		}

		//ComboBox ActionListener
		itemsCB.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				String comboValue = itemsCB.getSelectedItem().toString();
				if(comboValue.equals("Select Item")) {
					itemNameTF.setText("");
				}
				else {
					//set Value in textField according to the ComboBox Selection
					for(int i = 0; i < Items.itemsArray.size(); i++) {
						if (itemsCB.getSelectedIndex()-1 == i) {
							itemNameTF.setText(Items.itemsArray.get(i).getName());
						}
					}
				}
			}
		});

		itemsInCartList.setBackground(new Color(204, 204, 255));
		itemsInCartList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0, 0, 0)));
		itemsInCartList.setFont(new Font("Algerian", 1, 18)); 
		itemsInCartList.setForeground(new Color(204, 0, 51));

		itemsInCartList.setSelectionBackground(new Color(204, 153, 255));
		scroll.setViewportView(itemsInCartList);

		itemsInCartLabel.setFont(new Font("Arial", 1, 24)); 
		itemsInCartLabel.setText("Items in the Cart");

		itemNameTF.setEditable(false);		//disable userInput for the textField

		addCustomerButton.setFont(new Font("Arial", 1, 14)); 
		addCustomerButton.setText("Add a Customer");
		addCustomerButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				EmployeeAddCustomer f = new EmployeeAddCustomer();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
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
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});

		modifyItemsButton.setFont(new Font("Arial", 1, 14)); 
		modifyItemsButton.setText("Modify Items");
		modifyItemsButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				ModifyItems f = new ModifyItems();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(ModifyItems.EXIT_ON_CLOSE);
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

		pOrdersButton.setFont(new Font("Arial", 1, 14)); 
		pOrdersButton.setText("Pending Orders");
		pOrdersButton.addActionListener (new ActionListener () {
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

		quantLabel.setFont(new Font("Arial", 1, 14)); 
		quantLabel.setText("Quantity");

		quantTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '1') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (quantTF.getText().length() >= 1 ) // limit textField to 1 characters
					e.consume(); 
			}
		});

		addItemButton.setFont(new Font("Arial", 1, 14)); 
		addItemButton.setText("Add Item to the Cart");
		DefaultListModel dlm = new DefaultListModel();

		// auto populate the cart upon launch of the logged in
		String currentUser = getCurrentUser();
		itemsInCart.clear();

		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			final String queryCheck = "SELECT * from cart";
			ResultSet rs = stmt.executeQuery(queryCheck);

			while (rs.next()) {
				if(rs.getString("username").equals(currentUser)) {
					dlm.addElement(rs.getString("itemname") + " x" + rs.getString("quantity"));
					itemsInCart.add(Items.findItem(rs.getString("itemname")));
					itemsInCart.get(itemsInCart.size() - 1).setQuantity(Integer.parseInt(rs.getString("quantity")));
					itemsInCartList.setModel(dlm);
				}
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
			System.exit(0);
		}

		//addItem actionListener
		addItemButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				try {
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery("select quantity FROM item where itemname = '"+ itemNameTF.getText().trim() +"';");
					int availQuant= 0;
					while(rs.next()) {
						availQuant = Integer.parseInt(rs.getString("QUANTITY"));
						break;
					}

					if (availQuant < Integer.parseInt(quantTF.getText()) && availQuant > 0) {
						JOptionPane.showMessageDialog(null,  "Only " + availQuant + " quantity of " + itemNameTF.getText().trim() + " is available" , "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						itemNameTF.setText("");
						quantTF.setText("");
						itemsCB.setSelectedIndex(0);
					}
					else if (availQuant <= 0) {
						JOptionPane.showMessageDialog(null,  itemNameTF.getText().trim() + " is out of stock" , "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						itemNameTF.setText("");
						quantTF.setText("");
						itemsCB.setSelectedIndex(0);
					}
					else {
						int quant = 0;
						while(!(quantTF.getText().equals(""))) {
							quant = Integer.parseInt(quantTF.getText());
							break;
						}

						boolean isDuplicateItem = false;

						for (int i = 0; i < itemsInCart.size(); i++) {

							String s = itemsInCart.get(i).getName();
							if (s.equals(itemNameTF.getText().trim())) {
								isDuplicateItem = true;
								JOptionPane.showMessageDialog(null, "Duplicate Item Orders. Please use Update Item Button to change the quantity.", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
								break;
							}
						}

						if (itemNameTF.getText().equals("") || (itemNameTF.getText().equals("") && quantTF.getText().equals(""))) {
							JOptionPane.showMessageDialog(null, "Please Select the Item", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						else if(quantTF.getText().equals("") && (!(itemNameTF.getText().equals("")))) {
							JOptionPane.showMessageDialog(null, "Please Enter the Quantity", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						else if(!(quant > 0 && quant <= 5)) {
							JOptionPane.showMessageDialog(null, "Quantity should be between 1 and 5 inclusive", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						else if(!isDuplicateItem){
							dlm.addElement(itemNameTF.getText() + " x" + quantTF.getText());
							itemsInCartList.setModel(dlm);

							Items matchedItem = null;

							int itemQuantity = Integer.parseInt(quantTF.getText());
							for(int i = 0; i < Items.itemsArray.size(); i++) {

								String itemName = itemsCB.getSelectedItem().toString();
								int itemNameLength = itemName.length();
								if(Items.itemsArray.get(i).getName().equals(itemNameTF.getText().substring(0, itemNameLength-8).trim())) {

									matchedItem = Items.itemsArray.get(i);
									itemsInCart.add(Items.itemsArray.get(i));

									for(int j = 0; j < itemsInCart.size(); j++) {
										if(itemName.trim().equals(itemsInCart.get(j).toString())) {
											itemsInCart.get(j).setQuantity(Integer.parseInt(quantTF.getText()));
										}
									}

									try {
										c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
										c.setAutoCommit(false);
										stmt = c.createStatement();
										String sql = "INSERT INTO CART (" + "USERNAME, ITEMNAME, QUANTITY)" 
												+ "VALUES('" + currentUser + "', '" + matchedItem.getName() + "','" + itemQuantity + "');";
										stmt.executeLargeUpdate(sql);

										c.commit();
										c.close();
										stmt.close();
									}
									catch (Exception e1) {
										e1.printStackTrace();
										System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
										System.exit(0);
									}
								}
							}
						}
					}
				}
				catch (Exception e1){
					e1.printStackTrace();
					System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
					System.exit(0);
				}

				itemNameTF.setText("");
				quantTF.setText("");
				itemsCB.setSelectedIndex(0);
			}
		});

		verLabel.setFont(new Font("Arial", 1, 18)); 
		verLabel.setForeground(new Color(0, 153, 153));
		verLabel.setText("Version 0.1.0");

		dltItemButton.setFont(new Font("Arial", 1, 14)); 
		dltItemButton.setText("Delete Item ");
		dltItemButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if(itemsInCartList.getSelectedIndex() > -1) {
					String itemNameWithQuantity = itemsInCartList.getSelectedValue().toString();
					String itemName = itemNameWithQuantity.substring(0, itemNameWithQuantity.length() - 3);

					for(int i = 0; i < itemsInCart.size(); i++) {
						if(itemName.trim().equals(itemsInCart.get(i).getName())) {
							try {
								c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
								c.setAutoCommit(false);
								stmt = c.createStatement();
								final String deleteQuery = "DELETE from cart where username = '" + getCurrentUser() + "' AND itemname = '" + itemsInCart.get(i).getName() + "';";
								stmt.executeUpdate(deleteQuery);
								c.commit();
							}
							catch (Exception e1) {
								e1.printStackTrace();
								System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
								System.exit(0);
							}
							itemsInCart.remove(i);
						}
					}
					dlm.removeElementAt(itemsInCartList.getSelectedIndex());

				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select an item from the Cart", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		updateButton.setFont(new Font("Arial", 1, 14)); 
		updateButton.setText("Update Item");
		

		updateButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if(!(itemsInCartList.isSelectionEmpty())) {
					
					String updatedQuant = JOptionPane.showInputDialog(null, "Update Quantity", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					if (updatedQuant == null || updatedQuant.length() > 0) {
						inRange = true;
					}
					if (!(updatedQuant == null)) {
						if (updatedQuant.length() > 0 &&((Integer.parseInt(updatedQuant) > 5) || (Integer.parseInt(updatedQuant) <= 0))){
							inRange = false;
							System.out.println("i got you");
						}
					}
					while (!inRange) {
						if (updatedQuant == null || updatedQuant.length() > 0) {
							inRange = true;
						}
						JOptionPane.showMessageDialog(null, "Item quantity should be between 1 and 5 inclusive", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						updatedQuant = JOptionPane.showInputDialog(null, "Update Quantity", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						if (!(updatedQuant == null || updatedQuant.length()==0)) {
							if ((Integer.parseInt(updatedQuant) > 0 && Integer.parseInt(updatedQuant) <= 5)){
								inRange = true;
							}
							else {
								inRange = false;
							}
						}
					}

					if(!(updatedQuant == null) && updatedQuant.length()>0) {
						String itemName = itemsInCartList.getSelectedValue().toString();
						String itemNameSubString = itemName.substring(0,itemName.length()-3);
						dlm.removeElementAt(itemsInCartList.getSelectedIndex());
						dlm.addElement(itemNameSubString  + " x" + updatedQuant);
						itemsInCartList.setModel(dlm);

						try {
							c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
							c.setAutoCommit(false);
							stmt = c.createStatement();

							for (int i = 0; i< itemsInCart.size(); i++) {
								if (itemsInCart.get(i).getName().equals(itemName.trim())) {
									itemsInCart.get(i).setQuantity(Integer.parseInt(updatedQuant));
								}
							}
							final String queryCheck = "UPDATE cart set quantity = '" + updatedQuant + "' where username = '" + 
									getCurrentUser() + "' AND itemname = '" + itemNameSubString + "';";

							stmt.executeLargeUpdate(queryCheck);
							c.commit();

							for (Items i : itemsInCart){
								if (i.getName().equals(itemNameSubString)){
									i.setQuantity(Integer.parseInt(updatedQuant));
								}
							}
						}
						catch (Exception e1) {
							e1.printStackTrace();
							System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
							System.exit(0);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select an item from the Cart", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		//check if item has enough quantity
		placeOrderButton.setFont(new Font("Arial", 1, 14)); 
		placeOrderButton.setText("Place Order");

		//placeOrder ActionListener
		placeOrderButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);			//rounds up to the next decimal

				double price = 0;
				double itemPrice = 0;
				double orderPrice = 0;
				int updatedQuant = 0;
				int availQuant= 0;
				int orderSize = itemsInCartList.getModel().getSize();

				if (!(itemsInCart.size() == 0)) {

					for(int j = 0; j < itemsInCart.size(); j++ ) {

						int quantOfItem = itemsInCart.get(j).getQuantity();

						String itemInCartList = itemsInCart.get(j).getName();

						try {
							c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
							stmt = c.createStatement();
							ResultSet rs = stmt.executeQuery("select QUANTITY FROM item where itemname = '"+ itemInCartList +"';");

							while(rs.next()) {
								availQuant = Integer.parseInt(rs.getString("QUANTITY"));
								break;
							}
							if (availQuant < quantOfItem && availQuant > 0) {
								JOptionPane.showMessageDialog(null,  "Only " + availQuant + " quantity of " + itemInCartList + " is available. Item will be removed from the cart." , "" + "Message", JOptionPane.INFORMATION_MESSAGE);
								itemsInCart.remove(itemsInCart.get(j));
								itemsInCartList.remove(j);

								try {
									c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
									c.setAutoCommit(false);
									stmt = c.createStatement();
									final String deleteQuery = "DELETE from cart where username = '" + getCurrentUser() + "' AND itemid = '"+ itemInCartList +"';";

									stmt.executeUpdate(deleteQuery);
									c.commit();
								}
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}
							}

							else if(availQuant <= 0) {
								JOptionPane.showMessageDialog(null,  itemInCartList + " is out of stock. Item will be removed from the cart." , "" + "Message", JOptionPane.INFORMATION_MESSAGE);


								try {
									c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
									c.setAutoCommit(false);
									stmt = c.createStatement();
									final String deleteQuery = "DELETE from cart where username = '" + getCurrentUser() + "' AND itemid = '"+ itemInCartList +"';";

									stmt.executeUpdate(deleteQuery);
									c.commit();
								}
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}
								itemsInCart.remove(itemsInCart.get(j));
								itemsInCartList.remove(j);
							}
							else {
								try {
									c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
									stmt = c.createStatement();
									ResultSet rs0 = stmt.executeQuery("select quantity FROM item where itemname = '"+ itemInCartList +"';");

									while(rs0.next()) {
										availQuant = Integer.parseInt(rs0.getString("QUANTITY"));
									}
									try {
										c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
										stmt = c.createStatement();
										ResultSet rs1 = stmt.executeQuery("select price FROM item where itemname = '"+ itemInCartList +"';");
										while(rs1.next()) {

											price = Double.parseDouble(rs1.getString("PRICE"));
										}

										itemPrice = Double.parseDouble(df.format(quantOfItem * price));
										orderPrice = Double.parseDouble(df.format(orderPrice + itemPrice));

										updatedQuant = availQuant - quantOfItem;

										try {

											c.setAutoCommit(false);
											stmt = c.createStatement();
											String sql = "UPDATE item set quantity = '" + updatedQuant + "' where itemname = '"+itemInCartList+"'";
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
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}

								try {
									c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
									c.setAutoCommit(false);
									stmt = c.createStatement();
									final String deleteQuery = "DELETE from cart where username = '" + getCurrentUser() + "';";

									stmt.executeUpdate(deleteQuery);
									c.commit();
								}
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}
							}
						}
						catch (Exception e1) {
							e1.printStackTrace();
							System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
							System.exit(0);
						}
					}
					dlm.removeAllElements();
					itemNameTF.setText("");
					quantTF.setText("");
					itemsCB.setSelectedItem(0);
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String orderId = uuid.substring(0,6);
					
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
					Date date = new Date(System.currentTimeMillis());
					String orderTime = formatter.format(date);
					System.out.println(orderTime);

					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						stmt = c.createStatement();
						c.setAutoCommit(false);
						stmt = c.createStatement();
						String sql = "INSERT INTO ORDERS (" + "USERNAME, ORDERID, ORDEREDAT, STATUS)"
								+ "VALUES('"+ getCurrentUser() +"','"+ orderId +"','"+ orderTime +"','PENDING');";
						stmt.executeLargeUpdate(sql);
						c.commit();
					}
					catch (Exception e1) {
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}	
					Random rand = new Random ();
					int deliveryTime = rand.nextInt(20) + 30;
					JOptionPane.showMessageDialog(null, "Your order of "+ orderSize + " items for $" + orderPrice + " has been placed.\nExpected Delivery Time is " + deliveryTime + " minutes.", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					itemsInCart.clear();
				}		
			}
		});
		
		

		//Using GroupLayout for the GUI
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
														.addGap(49, 49, 49))
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(itemsInCartLabel, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
														.addContainerGap())))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent(verLabel)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
														.addComponent(modifyItemsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(pOrdersButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(signOutButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(addCustomerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(layout.createSequentialGroup()
														.addComponent(dltItemButton)
														.addGap(17, 17, 17)))
										.addGap(53, 53, 53)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																.addComponent(itemNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(quantLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(29, 29, 29)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent(itemNameTF, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
																.addComponent(quantTF, GroupLayout.PREFERRED_SIZE, 70,GroupLayout.PREFERRED_SIZE)))
												.addComponent(addItemButton)
												.addGroup(layout.createSequentialGroup()
														.addGap(29, 29, 29)
														.addComponent(updateButton)))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(placeOrderButton)
														.addGap(40, 40, 40)))
										.addContainerGap())))
				.addGroup(layout.createSequentialGroup()
						.addGap(197, 197, 197)
						.addComponent(itemsCB, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(18, 18, 18)
						.addComponent(itemsCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(33, 33, 33)
						.addComponent(itemsInCartLabel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(46, 46, 46)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent(addCustomerButton)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(itemNameLabel)
														.addComponent(itemNameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent(modifyItemsButton)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(quantLabel)
														.addComponent(quantTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(pOrdersButton)
												.addComponent(addItemButton))
										.addGap(18, 18, 18)
										.addComponent(signOutButton)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
										.addGap(18, 18, 18)))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(dltItemButton)
								.addComponent(updateButton)
								.addComponent(placeOrderButton))
						.addGap(83, 83, 83)
						.addComponent(verLabel)
						.addGap(31, 31, 31)));
		pack();
	}                        
}
