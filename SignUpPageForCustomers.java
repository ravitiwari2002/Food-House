/**
 * Author: Ravi Tiwari
 * Filename: SignUpPageForCustomers.java
 * Specifications: A Java file with JFrame which allows customers to signUp
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
import java.sql.Statement;
import java.sql.ResultSet;

public class SignUpPageForCustomers extends JFrame {

	//instance variables
	private JRadioButton customerRb = new JRadioButton();
	private JRadioButton employeeRb = new JRadioButton();
	private JButton signUpButton = new JButton();
	private JCheckBox showPwdCB = new JCheckBox();
	private JLabel fLabel = new JLabel();			
	private JLabel lLabel = new JLabel();
	private JLabel uLabel = new JLabel();
	private JLabel pwdLabel = new JLabel();
	private JLabel cPwdLabel = new JLabel();
	private JLabel eLabel = new JLabel();
	private JLabel phLabel = new JLabel();
	private JLabel addLabel = new JLabel();
	private JLabel posLabel = new JLabel();
	private JPasswordField pwdPF = new JPasswordField();
	private JPasswordField cPwdPF = new JPasswordField();
	private JTextField firstTF = new JTextField();
	private JTextField lastTF = new JTextField();
	private JTextField userTF = new JTextField();
	private JTextField eTF = new JTextField();
	private JTextField phTF = new JTextField();
	private JTextField addTF = new JTextField();
	private JTextField postTF = new JTextField();
	private JLabel signUpLabel = new JLabel();
	private JLabel welcomeLabel = new JLabel();

	static Connection c = null;
	static Statement stmt = null;


	//default constructor
	public SignUpPageForCustomers() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
		databaseConnection();
	}

	//making a connection with database
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

	//set look and feel of frame and set frame visible
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

		SignUpPageForCustomers frame = new SignUpPageForCustomers();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(SignUpPageForCustomers.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}

	private void design() {

		//add ActionListener for signUp Button. Data is added into Customers Table in Users Database
		signUpButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {


				//check if Password field match. if they do not match show Message Dialog
				if (!(String.valueOf(pwdPF.getPassword()).equals(String.valueOf(cPwdPF.getPassword())))) {
					JOptionPane.showMessageDialog(null, "Passwords didn't match. Try Again", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					pwdPF.setText("");
					cPwdPF.setText("");
				}

				// check from database if phone number, email or username matches
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
							if(rs2.getString("username").equals(userTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								userTF.setText("");
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
							if(rs5.getString("username").equals(userTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								userTF.setText("");
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

					String fName = firstTF.getText();
					String lName = lastTF.getText();
					String username = userTF.getText();
					String password = String.valueOf(pwdPF.getPassword());
					String email = eTF.getText();
					String phone = phTF.getText();
					String address = addTF.getText();
					String zip = postTF.getText();

					if (!(firstTF.getText().equals("") || lastTF.getText().equals("") ||userTF.getText().equals("")
							||String.valueOf(pwdPF.getPassword()).equals("") || eTF.getText().equals("") || phTF.getText().equals("")
							|| addTF.getText().equals("") || postTF.getText().equals(""))) {
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

							//change frame
							SuccessfulSignUp f = new SuccessfulSignUp();
							f.setResizable(false);
							f.setTitle("Casino Coffee");
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
					else {
						JOptionPane.showMessageDialog(null, "Please Fill All The Details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		//Add KeyListener for phone TextField and only takes number
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

		//Add KeyListener for postal TextField and only takes number
		postTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (postTF.getText().length() >= 6) // limit textfield to 6 characters
					e.consume(); 
			}
		});

		customerRb.setSelected(true);

		//Add ActionListener for show Password ComboBox
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

		//add KeyListener to PasswordField
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

		userTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (userTF.getText().length() >= 8 ) // limit textfield to 8 characters
					e.consume(); 
			}  
		});
		firstTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (firstTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}  
		});
		lastTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 

				if (lastTF.getText().length() >= 10 ) // limit textfield to 10 characters
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

		employeeRb.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {	
				SignUpPageForEmployees f1 = new SignUpPageForEmployees();
				f1.setResizable(false);
				f1.setTitle("Casino Coffee");
				f1.setLocationRelativeTo(null);
				f1.setVisible(true);
				dispose();
			}
		});

		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		signUpLabel.setFont(new Font("Arial", 1, 18)); 
		signUpLabel.setText("Sign Up");

		customerRb.setFont(new Font("Arial", 0, 14)); 
		customerRb.setText("Customer");

		employeeRb.setFont(new Font("Arial", 0, 14)); 
		employeeRb.setText("Employee");


		fLabel.setFont(new Font("Arial", 0, 14)); 
		fLabel.setText("First Name");

		lLabel.setFont(new Font("Arial", 0, 14)); 
		lLabel.setText("Last Name");

		uLabel.setFont(new Font("Arial", 0, 14)); 
		uLabel.setText("Username");

		pwdLabel.setFont(new Font("Arial", 0, 14)); 
		pwdLabel.setText("Password");

		cPwdLabel.setFont(new Font("Arial", 0, 14)); 
		cPwdLabel.setText("Confirm Password");

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

		signUpButton.setFont(new Font("Arial", 1, 14));
		signUpButton.setText("Sign Up");

		//Using Group layout to add components
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(105, 105, 105)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(posLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(addLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(phLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(eLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(cPwdLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
														.addComponent(pwdLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(uLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(customerRb)
														.addComponent(fLabel, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
														.addComponent(lLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGap(128, 128, 128)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(employeeRb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(userTF)
												.addComponent(lastTF)
												.addComponent(firstTF)
												.addComponent(pwdPF)
												.addComponent(cPwdPF)
												.addComponent(eTF)
												.addComponent(phTF)
												.addComponent(addTF)
												.addComponent(postTF))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(showPwdCB))
								.addGroup(layout.createSequentialGroup()
										.addGap(229, 229, 229)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent(signUpButton)
												.addComponent(signUpLabel))))
						.addContainerGap(67, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
						.addGap(54, 54, 54))
				);
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
						.addGap(43, 43, 43)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(firstTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(fLabel))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lLabel)
								.addComponent(lastTF,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(uLabel)
								.addComponent(userTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(pwdLabel)
								.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(showPwdCB))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(cPwdLabel)
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
								.addComponent(postTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
						.addComponent(signUpButton)
						.addGap(34, 34, 34)));
		pack();
	}                
}

