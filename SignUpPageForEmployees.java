/**
 * Author: Ravi Tiwari
 * Filename: SignUpPageForEmployees.java
 * Specifications: A Java file with JFrame which allows employees to signUp
 * For: CSE 205 - Final Project
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;


public class SignUpPageForEmployees extends JFrame {

	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel signUpLabel = new JLabel();
	private JRadioButton customerRb = new JRadioButton();
	private JRadioButton employeeRb = new JRadioButton();
	private JTextField fTF = new JTextField();
	private JLabel fLabel = new JLabel();
	private JLabel lLabel = new JLabel();
	private JLabel uLabel = new JLabel();
	private JLabel pwdLabel = new JLabel();
	private JLabel cPwdLabel = new JLabel();
	private JLabel eLabel = new JLabel();
	private JLabel phLabel = new JLabel();
	private JLabel addLabel = new JLabel();
	private JLabel posLabel = new JLabel();
	private JTextField lTF = new JTextField();
	private JTextField uTF = new JTextField();
	private JPasswordField pwdPF = new JPasswordField();
	private JPasswordField cPwdPF = new JPasswordField();
	private JTextField eTF = new JTextField();
	private JTextField phTF = new JTextField();
	private JTextField addTF = new JTextField();
	private JTextField posTF = new JTextField();
	private JCheckBox showPwdCB = new JCheckBox();
	private JButton signUpButton = new JButton();
	private JLabel positionLabel = new JLabel();
	private JTextField positionTF = new JTextField();

	static Connection c = null;
	static Statement stmt = null;

	static boolean managerRegistered = false;
	static boolean allDetailsFilled = false;

	//default constructor
	public SignUpPageForEmployees() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
		databaseConnection();
	}

	//make a connection with database
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

	//set look and feel of Frame and set frame visible
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

		SignUpPageForEmployees frame = new SignUpPageForEmployees();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(SignUpPageForEmployees.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}   

	private void managerRegistered() {

		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			stmt = c.createStatement();

			final String queryCheck1 = "SELECT * from employees";
			ResultSet rs1 = stmt.executeQuery(queryCheck1);
			while(rs1.next()) {
				if(rs1.getString("position").equalsIgnoreCase(positionTF.getText())) {
					managerRegistered = true;
					break;
				}
				else {
					managerRegistered = false;
				}
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
			System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
			System.exit(0);
		}
	}

	private void design() {

		//check if fields are empty or password matches
		signUpButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				if (!(String.valueOf(pwdPF.getPassword()).equals(String.valueOf(cPwdPF.getPassword())))) {
					JOptionPane.showMessageDialog(null, "Passwords didn't match. Try Again", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					pwdPF.setText("");
					cPwdPF.setText("");
				}

				managerRegistered();
				if (managerRegistered && positionTF.getText().length() > 0) {
					JOptionPane.showMessageDialog(null, "Manager is already registered", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					positionTF.setText("");
				}

				//check if phonenumber, email and username already exists in database
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
					}
					catch (Exception e1) {
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}
					String fName = fTF.getText();
					String lName = lTF.getText();
					String position = positionTF.getText();
					String username = uTF.getText();
					String password = String.valueOf(pwdPF.getPassword());
					String email = eTF.getText();
					String phone = phTF.getText();
					String address = addTF.getText();
					String zip = posTF.getText();


					//add data to employees table
					if (!(fTF.getText().equals("") || lTF.getText().equals("") ||positionTF.getText().equals("") || uTF.getText().equals("")
							||String.valueOf(pwdPF.getPassword()).equals("") || eTF.getText().equals("") || phTF.getText().equals("")
							|| addTF.getText().equals("") || posTF.getText().equals(""))) {
						
						try {
							c.setAutoCommit(false);
							stmt = c.createStatement();
							String sql = "INSERT INTO EMPLOYEES (" + "FIRSTNAME, LASTNAME,POSITION, USERNAME, PASSWORD, EMAILADDRESS, PHONENUMBER, ADDRESS, POSTALCODE, STATUS)" 
									+ "VALUES('"+ fName +"','" + lName +"' ,'"+ position +"' ,'" + username + "','" + password +"' ,'" + email +"','" 
									+ phone +"','" + address +"','" + zip +"', 'SIGNED OUT');";

							stmt.executeLargeUpdate(sql);
							c.commit();
							System.out.println("Data added to Employees");

							//change frame
							SuccessfulSignUp f = new SuccessfulSignUp();
							f.setResizable(false);
							f.setTitle("Casino Coffee");
							f.setDefaultCloseOperation(SuccessfulSignUp.EXIT_ON_CLOSE);
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

		//add KeyListener and ActionListeners

		phTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (phTF.getText().length() >= 10) // limit textfield to 10 characters
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





		employeeRb.setSelected(true);

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
		positionTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (positionTF.getText().length() >= 15 ) // limit textfield to 15 characters
					e.consume(); 
			}  
		});

		customerRb.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				SignUpPageForCustomers f = new SignUpPageForCustomers();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setLocationRelativeTo(null);
				f.setDefaultCloseOperation(SignUpPageForCustomers.EXIT_ON_CLOSE);
				f.setVisible(true);
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

		positionLabel.setFont(new Font("Arial", 0, 14)); 
		positionLabel.setText("Position");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
						.addGap(54, 54, 54))
				.addGroup(layout.createSequentialGroup()
						.addGap(229, 229, 229)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(signUpButton)
								.addComponent(signUpLabel))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addGap(105, 105, 105)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(posLabel,GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
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
										.addComponent(positionLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
						.addGap(128, 128, 128)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(employeeRb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lTF)
												.addComponent(fTF)
												.addComponent(positionTF, GroupLayout.PREFERRED_SIZE, 89,GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(showPwdCB))
												.addComponent(uTF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
												.addComponent(cPwdPF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
												.addComponent(eTF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
												.addComponent(phTF,GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
												.addComponent(addTF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
												.addComponent(posTF, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(67, Short.MAX_VALUE))))
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
								.addComponent(fTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(fLabel))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lLabel)
								.addComponent(lTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(positionLabel)
								.addComponent(positionTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(uLabel)
								.addComponent(uTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
						.addGap(22, 22, 22)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(pwdLabel)
								.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(showPwdCB))
						.addGap(16, 16, 16)
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
								.addComponent(addTF, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(posLabel)
								.addComponent(posTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
						.addComponent(signUpButton)
						.addGap(44, 44, 44)));
		pack();
	}                    
}

