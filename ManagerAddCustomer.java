/**
 * Author: Ravi Tiwari
 * Filename: ManagerAddCustomer.java
 * Specifications: A Java file with JFrame which allows Manager to add customers
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class ManagerAddCustomer extends JFrame {

	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel fillLabel = new JLabel();
	private JLabel fLabel = new JLabel();
	private JLabel lLabel = new JLabel();
	private JLabel uLabel = new JLabel();
	private JLabel pwdLabel = new JLabel();
	private JLabel confirmPwdLabel = new JLabel();
	private JLabel eLabel = new JLabel();
	private JLabel phLabel = new JLabel();
	private JLabel addLabel = new JLabel();
	private JLabel posLabel = new JLabel();
	private JTextField fTF = new JTextField();
	private JTextField lTF = new JTextField();
	private JTextField uTF = new JTextField();
	private JTextField eTF = new JTextField();
	private JPasswordField pwdPF = new JPasswordField();
	private JPasswordField cPwdPF = new JPasswordField();
	private JTextField phTF = new JTextField();
	private JTextField addTF = new JTextField();
	private JTextField posTF = new JTextField();
	private JButton addCustomerButton = new JButton();
	private JButton backButton = new JButton();
	private JCheckBox showPwdCB = new JCheckBox();

	static Connection c = null;
	static Statement stmt = null;

	//default constructor
	public ManagerAddCustomer() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
		databaseConnection();
	}

	//connect to the database
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
			System.out.println("Nimbus not found");
		}

		ManagerAddCustomer frame = new ManagerAddCustomer();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(ManagerAddCustomer.EXIT_ON_CLOSE);
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

		//set font, foreground, and text of the components
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		fillLabel.setFont(new Font("Arial", 0, 18)); 
		fillLabel.setForeground(new Color(0, 102, 102));
		fillLabel.setText("Fill all the details to add Customer");

		fLabel.setFont(new Font("Arial", 0, 14));
		fLabel.setText("First Name");

		lLabel.setFont(new Font("Arial", 0, 14)); 
		lLabel.setText("Last Name");

		uLabel.setFont(new Font("Arial", 0, 14)); 
		uLabel.setText("Username");

		pwdLabel.setFont(new Font("Arial", 0, 14)); 
		pwdLabel.setText("Password");

		confirmPwdLabel.setFont(new Font("Arial", 0, 14));
		confirmPwdLabel.setText("Confirm Password");

		eLabel.setFont(new Font("Arial", 0, 14)); 
		eLabel.setText("Email Address");

		phLabel.setFont(new Font("Arial", 0, 14)); 
		phLabel.setText("Phone Number");

		addLabel.setFont(new Font("Arial", 0, 14)); 
		addLabel.setText("Street Address");

		posLabel.setFont(new Font("Arial", 0, 14)); 
		posLabel.setText("Postal Code");

		showPwdCB.setFont(new Font("Arial", 0, 14)); 
		showPwdCB.setText("Show Password");

		backButton.setFont(new Font("Arial", 1, 14)); 
		backButton.setText("Back");

		backButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//when button is clicked frame changes
				ManagerLogIn f = new ManagerLogIn();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});
		
		addCustomerButton.setFont(new Font("Arial", 1, 14)); 
		addCustomerButton.setText("Add Customer");

		addCustomerButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//check if all fields are filled
				if (fTF.getText().equals("") || lTF.getText().equals("") ||uTF.getText().equals("")
						||String.valueOf(pwdPF.getPassword()).equals("") || eTF.getText().equals("") || phTF.getText().equals("")
						|| addTF.getText().equals("") || posTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill All The Details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				
				//check if passwords match
				else if (!(String.valueOf(pwdPF.getPassword()).equals(String.valueOf(cPwdPF.getPassword())))) {
					JOptionPane.showMessageDialog(null, "Passwords didn't match. Try Again", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					pwdPF.setText("");
					cPwdPF.setText("");
				}

				//check if username, phonenumber or email address already exists in database
				else {
					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						stmt = c.createStatement();

						final String queryCheck = "SELECT * from customers";
						ResultSet rs = stmt.executeQuery(queryCheck);
						while(rs.next()) {
							if(rs.getString("emailaddress").equals(eTF.getText())) {
								JOptionPane.showMessageDialog(null,"Email is already registered","",JOptionPane.INFORMATION_MESSAGE);
								eTF.setText("");
								break;
							}
						} 
						final String queryCheck1 = "SELECT * from customers";
						ResultSet rs1 = stmt.executeQuery(queryCheck1);
						while(rs1.next()) {
							if(rs1.getString("phonenumber").equals(phTF.getText())) {
								JOptionPane.showMessageDialog(null,"Phone Number is already registered","",JOptionPane.INFORMATION_MESSAGE);
								phTF.setText("");
								break;
							}
						}
						final String queryCheck2 = "SELECT * from customers";
						ResultSet rs2 = stmt.executeQuery(queryCheck2);
						while(rs2.next()) {
							if(rs2.getString("username").equals(uTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								uTF.setText("");
								break;
							}

						}					
						final String queryCheck3 = "SELECT * from employees";

						ResultSet rs3 = stmt.executeQuery(queryCheck3);
						while(rs3.next()) {
							if(rs3.getString("emailaddress").equals(eTF.getText())) {
								JOptionPane.showMessageDialog(null,"Email is already registered","",JOptionPane.INFORMATION_MESSAGE);
								eTF.setText("");
								break;
							}
						} 

						final String queryCheck4 = "SELECT * from employees";

						ResultSet rs4 = stmt.executeQuery(queryCheck4);
						while(rs4.next()) {
							if(rs4.getString("phonenumber").equals(phTF.getText())) {
								JOptionPane.showMessageDialog(null,"Phone Number is already registered","",JOptionPane.INFORMATION_MESSAGE);
								phTF.setText("");
								break;
							}
						} 

						final String queryCheck5 = "SELECT * from employees";
						ResultSet rs5 = stmt.executeQuery(queryCheck5);
						while(rs5.next()) {
							if(rs5.getString("username").equals(uTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								uTF.setText("");
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
					
					String fName = fTF.getText();
					String lName = lTF.getText();
					String username = uTF.getText();
					String password = String.valueOf(pwdPF.getPassword());
					String email = eTF.getText();
					String phone = phTF.getText();
					String address = addTF.getText();
					String zip = posTF.getText();

					if ((fName.equals("") || lName.equals("")|| username.equals("") || password.equals("") || email.equals("") || phone.equals("")
							|| address.equals("")|| zip.equals(""))) {
						JOptionPane.showMessageDialog(null,"Please Fill All the Required Details","",JOptionPane.INFORMATION_MESSAGE);

					}
					
					//add data to the customers table in database
					else {
						try {
							c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
							c.setAutoCommit(false);
							stmt = c.createStatement();
							String sql = "INSERT INTO CUSTOMERS (" + "FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAILADDRESS, PHONENUMBER, ADDRESS, POSTALCODE, STATUS)" 
									+ "VALUES('"+ fName +"','" + lName +"' ,'"+ username + "','" + password +"' ,'" + email +"','" 
									+ phone +"','" + address +"','" + zip +"', 'SIGNED OUT');";

							stmt.executeLargeUpdate(sql);
							c.commit();
							c.close();
							stmt.close();
							System.out.println("Data added to Customers");

							//frame changes on successful data addition
							ManagerAddedCustomerMessage f = new ManagerAddedCustomerMessage();
							f.setResizable(false);
							f.setTitle("Casino Coffee");
							f.setDefaultCloseOperation(ManagerAddedCustomerMessage.EXIT_ON_CLOSE);
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
						catch (Exception e1) {
							e1.printStackTrace();
							System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
							System.exit(0);
						}
					}
				}

			}
		});

		//add KeyListener and ActionListener to the components
		phTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (phTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}
		});
		posTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (posTF.getText().length() >= 6) // limit textfield to 6 characters
					e.consume(); 
			}
		});

		showPwdCB.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (showPwdCB.isSelected()) {
					pwdPF.setEchoChar((char) 0);
					cPwdPF.setEchoChar((char)0);
				} else {
					pwdPF.setEchoChar('*');
					cPwdPF.setEchoChar('*');
				}
			}
		});

		pwdPF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (String.valueOf(pwdPF.getPassword()).length() >= 12 ) // limit textfield to 12 characters
					e.consume(); 
			}  
		});

		cPwdPF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (String.valueOf(cPwdPF.getPassword()).length() >= 12 ) // limit textfield to 12 characters
					e.consume(); 
			}  
		});

		uTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (uTF.getText().length() >= 8 ) // limit textfield to 8 characters
					e.consume(); 
			}  
		});
		fTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 

				if (fTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}  
		});
		lTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (lTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}  
		});

		addTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (addTF.getText().length() >= 50 ) // limit textfield to 50 characters
					e.consume(); 
			}  
		});

		eTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (eTF.getText().length() >= 30 ) // limit textfield to 30 characters
					e.consume(); 
			}  
		});

		//using GroupLayout to add components to the frame
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(welcomeLabel)
						.addGap(40, 40, 40))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(backButton)
										.addGap(82, 82, 82)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(fillLabel, GroupLayout.Alignment.TRAILING)
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																														.addGroup(layout.createSequentialGroup()
																																.addComponent(fLabel)
																																.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																														.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																																.addComponent(lLabel)
																																.addGap(99, 99, 99)))
																												.addGroup(layout.createSequentialGroup()
																														.addComponent(uLabel)
																														.addGap(102, 102, 102)))
																										.addGroup(layout.createSequentialGroup()
																												.addComponent(eLabel)
																												.addGap(77, 77, 77)))
																								.addGroup(layout.createSequentialGroup()
																										.addComponent(pwdLabel)
																										.addGap(105, 105, 105)))
																						.addGroup(layout.createSequentialGroup()
																								.addComponent(confirmPwdLabel)
																								.addGap(51, 51, 51)))
																				.addGroup(layout.createSequentialGroup()
																						.addComponent(phLabel)
																						.addGap(72, 72, 72)))
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(addLabel)
																				.addGap(74, 74, 74)))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(posLabel)
																		.addGap(90, 90, 90)))
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																.addComponent(pwdPF)
																.addComponent(fTF)
																.addComponent(lTF)
																.addComponent(uTF)
																.addComponent(eTF)
																.addComponent(cPwdPF)
																.addComponent(phTF)
																.addComponent(addTF)
																.addComponent(posTF, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(showPwdCB))
								.addGroup(layout.createSequentialGroup()
										.addGap(227, 227, 227)
										.addComponent(addCustomerButton)))
						.addContainerGap(38, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(fillLabel)
								.addComponent(backButton))
						.addGap(32, 32, 32)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(fLabel)
								.addComponent(fTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lLabel)
								.addComponent(lTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(uLabel)
								.addComponent(uTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(pwdLabel)
								.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(showPwdCB))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(confirmPwdLabel)
								.addComponent(cPwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(eLabel)
								.addComponent(eTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phLabel)
								.addComponent(phTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(addLabel)
								.addComponent(addTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(posLabel)
								.addComponent(posTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(29, 29, 29)
						.addComponent(addCustomerButton)
						.addContainerGap(59, Short.MAX_VALUE)));
		pack();
	}                                           
}

