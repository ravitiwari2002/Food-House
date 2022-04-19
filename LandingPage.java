/**
 * Author: Ravi Tiwari
 * Filename: LandingPage.java (this is the driver class of this project)
 * Specifications: A Java file with JFrame and check login credentials with database
 * For: CSE 205 - Final Project
 */


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LandingPage extends JFrame {

	//instance variables
	private JLabel forgotPwdLabel = new JLabel();
	private JLabel forgotUsernameLabel = new JLabel();
	private JPasswordField pwdPF = new JPasswordField();
	private JTextField usernameTF = new JTextField();
	private JLabel pwdLabel = new JLabel();
	private JCheckBox showPwdCheckBox = new JCheckBox();
	private JButton signInButton = new JButton();
	private JLabel signInLabel = new JLabel();
	private JLabel signUpLabel = new JLabel();
	private JLabel usernameLabel = new JLabel();
	private JLabel welcomeLabel = new JLabel();

	static Connection c = null;
	static Statement stmt = null;

	//default constructor
	public LandingPage() {
		design();

	}
	public static void main(String[] args) {
		viewFrame();
		databaseConnection();
		//createTablesInDB();
		//insertIntoItem();

	}

	//connecting with a database
	private static void databaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			System.out.println("You are connected boy");

			c.setAutoCommit(false);
			stmt = c.createStatement();
			final String queryCheck = "SELECT * from item";
			ResultSet rs = stmt.executeQuery(queryCheck);

			// auto populate our static itemsArray ArrayList from database
			while (rs.next()) {
				Items.itemsArray.add(new Items(rs.getString("itemname"), Double.parseDouble(rs.getString("price")), Integer.parseInt(rs.getString("quantity"))));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
	}

	//create tables in database
	private static void createTablesInDB() {
		try {
			
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE CUSTOMERS" + "(FIRSTNAME TEXT NOT NULL ,"+ "LASTNAME TEXT NOT NULL, " 
					+ "USERNAME TEXT NOT NULL,"+ "PASSWORD TEXT NOT NULL," + "EMAILADDRESS TEXT NOT NULL,"+""
					+ "PHONENUMBER TEXT NOT NULL,"+ "ADDRESS TEXT NOT NULL,"+"POSTALCODE TEXT NOT NULL, " + "STATUS TEXT NOT NULL)";
			stmt.executeUpdate(sql);

			String sql1 = "CREATE TABLE EMPLOYEES" + "(FIRSTNAME TEXT NOT NULL ,"+ "LASTNAME TEXT NOT NULL, " + "POSITION TEXT NOT NULL,"
					+ "USERNAME TEXT NOT NULL,"+ "PASSWORD TEXT NOT NULL," + "EMAILADDRESS TEXT NOT NULL,"+""
					+ "PHONENUMBER TEXT NOT NULL,"+ "ADDRESS TEXT NOT NULL,"+"POSTALCODE TEXT NOT NULL," + "STATUS TEXT NOT NULL)";
			stmt.executeUpdate(sql1);

			String sql2 = "CREATE TABLE CART" + "(USERNAME TEXT NOT NULL ," + "ITEMNAME TEXT NOT NULL ," + "QUANTITY INT NOT NULL)";
			stmt.executeUpdate(sql2);

			String sql3 ="CREATE TABLE ORDERS" + "(USERNAME TEXT NOT NULL ," + "ORDERID TEXT NOT NULL ,"+ "ORDEREDAT TEXT NOT NULL ," + "STATUS TEXT NOT NULL)";
			stmt.executeUpdate(sql3);
			System.out.println("Table Created");

			String sql4 = "CREATE TABLE ITEM" + "(ITEMNAME TEXT NOT NULL ," + "PRICE REAL ," + "QUANTITY INT NOT NULL)";
			stmt.executeUpdate(sql4);


		}
		catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
			System.exit(0);
		}
	}

	//Inserting Items in the database
	private static void insertIntoItem() {
		for(Items i : Items.itemsArray) {

			try {
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String sql = "INSERT INTO ITEM (" + "ITEMNAME, PRICE, QUANTITY)" 
						+ "VALUES('" + i.getName() + "', '" + i.getPrice() + "','" + i.getAvailableQuant() + "');";
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
	//method which change the status of user to signOut in database
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

	//method for frame look and feel and set it visible
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
		LandingPage frame = new LandingPage();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(LandingPage.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}  

	private void design() {

		//adding Key Listener to the Username TextField
		usernameTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (usernameTF.getText().length() >= 8 ) // limit textfield to 8 characters
					e.consume(); 
			}  
		});

		//add welcome Label 
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		//add signIn Label
		signInLabel.setFont(new Font("Arial", 1, 18)); 
		signInLabel.setText("Sign In");

		//add username Label
		usernameLabel.setFont(new Font("Arial", 0, 15)); 
		usernameLabel.setText("Username");

		//adding Key Listener to the Password Field
		pwdPF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (String.valueOf(pwdPF.getPassword()).length() >= 12 ) // limit textfield to 12 characters
					e.consume(); 
			}  
		});

		//add password label
		pwdLabel.setFont(new Font("Arial", 0, 15)); 
		pwdLabel.setText("Password");

		//add forgot password label
		forgotPwdLabel.setFont(new Font("Arial", 0, 14)); 
		forgotPwdLabel.setText("Forgot Password?");
		forgotPwdLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		//adding MouseListener to the forgot password Label
		forgotPwdLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					VerifyDetails f = new VerifyDetails();
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(VerifyDetails.EXIT_ON_CLOSE);
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				forgotPwdLabel.setText("Forgot Password?");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				forgotPwdLabel.setText("<html><a href=''>" + "Forgot Password?" + "</a></html>");
			}
		});

		//add checkbox
		showPwdCheckBox.setFont(new Font("Arial", 0, 14)); 
		showPwdCheckBox.setText("Show Password");

		//adding Action Listener to the CheckBox
		showPwdCheckBox.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (showPwdCheckBox.isSelected()) {
					pwdPF.setEchoChar((char) 0);
				} else {
					pwdPF.setEchoChar('*');
				}
			}
		});

		//adding Action Listener to the signInButton
		signInButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				int count =0;
				int count1 = 0;
				try {
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery("select * from customers;");

					String inputUsername = usernameTF.getText();
					String inputPassword = String.valueOf(pwdPF.getPassword());

					//match data from the customers' table
					while(rs.next()) {
						String username = rs.getString("USERNAME");
						String password = rs.getString("PASSWORD");
						if(username.equals(inputUsername) && password.equals(inputPassword)) {

							try {
								c.setAutoCommit(false);
								stmt = c.createStatement();
								String sql = "UPDATE CUSTOMERS set STATUS = 'SIGNED IN' where username = '"+username+"'";
								stmt.executeUpdate(sql);
								c.commit();

							}
							catch (Exception e1) {
								e1.printStackTrace();
								System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
								System.exit(0);
							}				

							try {
								CustomerLogIn f = new CustomerLogIn();
								f.setResizable(false);
								f.setTitle("Casino Coffee");
								f.setLocationRelativeTo(null);
								f.setVisible(true);
								f.setDefaultCloseOperation(CustomerLogIn.EXIT_ON_CLOSE);
								f.addWindowListener(new WindowAdapter()
								{
									@Override
									public void windowClosing(WindowEvent e)
									{
										super.windowClosing(e);
										try {
											c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
											stmt = c.createStatement();
											ResultSet rs = stmt.executeQuery("select username FROM customers where status = 'SIGNED IN';");
											String username= "";
											while(rs.next()) {
												username = rs.getString("USERNAME");
											}
											try {
												c.setAutoCommit(false);
												stmt = c.createStatement();
												String sql = "UPDATE CUSTOMERS set STATUS = 'SIGNED OUT' where username = '"+username+"'";
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
								});
								dispose();

							} catch (Exception ex) {
								ex.printStackTrace();
							}
							count++;
							break;
						}
					}
					if (count == 0) {

						ResultSet rs1 = stmt.executeQuery("select * from employees;");
						String inputUsername1 = usernameTF.getText();
						String inputPassword1 = String.valueOf(pwdPF.getPassword());
						String position = "Manager";

						// match data with the employees' table
						while(rs1.next()) {
							String position1 = rs1.getString("POSITION");
							String username1 = rs1.getString("USERNAME");
							String password1 = rs1.getString("PASSWORD");

							//if position does not match to manager then user logIn as an Employee
							if(username1.equals(inputUsername1) && password1.equals(inputPassword1) && (!(position1.equalsIgnoreCase(position)))) {
								try {
									c.setAutoCommit(false);
									stmt = c.createStatement();
									String sql = "UPDATE EMPLOYEES set STATUS = 'SIGNED IN' where username = '"+username1+"'";
									stmt.executeUpdate(sql);
									c.commit();

								}
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}			
								try {
									EmployeeLogIn f = new EmployeeLogIn();
									f.setResizable(false);
									f.setTitle("Casino Coffee");
									f.setLocationRelativeTo(null);
									f.setVisible(true);
									f.setDefaultCloseOperation(EmployeeLogIn.EXIT_ON_CLOSE);
									//WindowListener, if user closes windows he will get signOut in database
									f.addWindowListener(new WindowAdapter()
									{
										@Override
										public void windowClosing(WindowEvent e)
										{
											super.windowClosing(e);
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
									});
									dispose();

								} catch (Exception ex) {
									ex.printStackTrace();
								}
								count1++;
								break;
							}
							//if position matches to manager then user logIn as a Manager
							if(username1.equals(inputUsername1) && password1.equals(inputPassword1) && position1.equalsIgnoreCase(position) ) {
								try {
									c.setAutoCommit(false);
									stmt = c.createStatement();
									String sql = "UPDATE EMPLOYEES set STATUS = 'SIGNED IN' where username = '"+username1+"'";
									stmt.executeUpdate(sql);
									c.commit();
								}
								catch (Exception e1) {
									e1.printStackTrace();
									System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
									System.exit(0);
								}			

								try {
									ManagerLogIn f = new ManagerLogIn();
									f.setResizable(false);
									f.setTitle("Casino Coffee");
									f.setLocationRelativeTo(null);
									f.setVisible(true);
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
									dispose();
								} 
								catch (Exception ex) {
									ex.printStackTrace();
								}
								count1++;
								break;
							}
						}
					}
					// if data entered does not matches with customers and employees tables then pop up appear
					if (count == 0 && count1 == 0){
						JOptionPane.showMessageDialog(null, "Invalid Details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
						count1++;
					}
					stmt.close();
					c.close();
				}
				catch (Exception e1) {
					e1.printStackTrace();
					System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
					System.exit(0);
				}
			}
		});

		//forgot Username Label
		forgotUsernameLabel.setFont(new Font("Arial", 0, 14)); 
		forgotUsernameLabel.setText("Forgot Username?");
		forgotUsernameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// adding MouseListener for forgotUsernameLabel
		forgotUsernameLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					VerifyDetails f = new VerifyDetails();
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(VerifyDetails.EXIT_ON_CLOSE);
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					dispose();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				forgotUsernameLabel.setText("Forgot Username?");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				forgotUsernameLabel.setText("<html><a href=''>" + "Forgot Username?" + "</a></html>");
			}
		});

		//signInButton Label
		signInButton.setFont(new Font("Arial", 1, 14)); 
		signInButton.setText("Sign In");

		//signUp Label
		signUpLabel.setFont(new Font("Arial", 0, 14)); 
		signUpLabel.setText("Don't Have An Account? Sign up");
		signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		//adding MouseListener to the signUpLabel
		signUpLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					SignUpChoice f = new SignUpChoice();
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					f.setResizable(false);
					f.setTitle("Casino Coffee");
					f.setDefaultCloseOperation(SignUpChoice.EXIT_ON_CLOSE);
					dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				signUpLabel.setText("Don't Have An Account? Sign up");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				signUpLabel.setText("<html><a href=''>" + "Don't Have An Account? Sign up" + "</a></html>");
			}
		});

		//using Group Layout to add Components
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(45, Short.MAX_VALUE)
						.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
						.addGap(42, 42, 42))
				.addGroup(layout.createSequentialGroup()
						.addGap(66, 66, 66)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(pwdLabel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(signInButton)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
												.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
												.addComponent(showPwdCheckBox))
										.addGap(33, 33, 33)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(forgotUsernameLabel)
												.addComponent(forgotPwdLabel))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(256, 256, 256)
										.addComponent(signInLabel))
								.addGroup(layout.createSequentialGroup()
										.addGap(164, 164, 164)
										.addComponent(signUpLabel)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(34, 34, 34)
						.addComponent(signInLabel)
						.addGap(36, 36, 36)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(forgotUsernameLabel))
						.addGap(28, 28, 28)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(forgotPwdLabel)
								.addComponent(pwdLabel)
								.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(showPwdCheckBox)
						.addGap(34, 34, 34)
						.addComponent(signInButton)
						.addGap(18, 18, 18)
						.addComponent(signUpLabel)
						.addContainerGap(65, Short.MAX_VALUE)));				

		pack();
	}                      
}
