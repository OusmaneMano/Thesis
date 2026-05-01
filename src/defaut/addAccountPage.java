package defaut;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class addAccountPage extends JFrame implements ActionListener{
	JPanel panel1, panel2;
	JLabel cust_ID, fname, lname, account_Num, initialDep, accountType, phone, email;
	JTextField cust_IDField, fnameField, lnameField, account_NumField, initialDepField, phoneField, emailField;
	JComboBox<String> accountTypeCombo;
	JButton okBtn, cancelBtn;
	String server = "jdbc:mysql://localhost/";
	String dbName = "bankapp";
	String username = "root";
	String pass = "";
	
	addAccountPage(){
		Container cont = this.getContentPane();
		cont.setLayout(null);
				
		this.setTitle("Add Account Menu");
		
		panel1 = new JPanel();
		panel1.setBounds(40,20,150,315);
		panel1.setBackground(Color.lightGray);
		panel1.setLayout(new GridLayout(8,1));
		panel2 = new JPanel();
		panel2.setBounds(190,20,250,310);
		panel2.setBackground(Color.lightGray);
		panel2.setLayout(new GridLayout(8,1,10,10));
		
		cust_ID = new JLabel("Customer ID: ");
		fname = new JLabel("First Name");
		lname = new JLabel("Last Name");
		account_Num = new JLabel("account Number");
		initialDep = new JLabel("Initial Deposit");
		accountType = new JLabel("Account Type");
		phone = new JLabel("Phone");
		email = new JLabel("Email");

		cust_IDField = new JTextField(20);
		fnameField = new JTextField(20);
		lnameField = new JTextField(20);
		account_NumField = new JTextField(20);
		initialDepField = new JTextField(20);
		phoneField = new JTextField(20);
		emailField = new JTextField(50);	
		
		accountTypeCombo = new JComboBox<>();
		accountTypeCombo.addItem("Saving");
		accountTypeCombo.addItem("Cheking");

		okBtn = new JButton("OK");
		okBtn.setBounds(190,360,100,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(340,360,100,30);
		
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		panel1.add(cust_ID);
		panel1.add(fname);
		panel1.add(lname);
		panel1.add(account_Num);
		panel1.add(initialDep);
		panel1.add(accountType);
		panel1.add(phone);
		panel1.add(email);
		
		panel2.add(cust_IDField);
		panel2.add(fnameField);
		panel2.add(lnameField);
		panel2.add(account_NumField);
		panel2.add(initialDepField);
		panel2.add(accountTypeCombo);
		panel2.add(phoneField);
		panel2.add(emailField);
		
		cont.add(panel1);
		cont.add(panel2);
		cont.add(okBtn);
		cont.add(cancelBtn);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(800,0,500,450);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		addAccountPage addAccountPage = new addAccountPage();
	}
	
	//Methode to add new Customer
	public void addCustomer() {
		
		String initialD = initialDep.getText();
		String customer_id = cust_IDField.getText();
		String firstName = fnameField.getText();
		String lastName = lnameField.getText(); 
		int account_Num = Integer.parseInt(account_NumField.getText());
		double initialDepot = Double.parseDouble(initialDepField.getText());
		int Phone = Integer.parseInt(phoneField.getText());
		String Email = emailField.getText();
		String AccountType = (String) accountTypeCombo.getSelectedItem() ;
		
		if(AccountType.equalsIgnoreCase("saving")) {
			AccountType = "Saving";
		}	
		else if (AccountType.equalsIgnoreCase("checking")){
			AccountType = "Checking";		
		}
		try {
			Connect conn = new Connect();
		
		String querry = "INSERT INTO customers(customer_ID, First_Name, Last_Name, Account_Num, Amount, Account_Type, Phone, Email)"
		+ "values('"+customer_id+"','"+firstName+"','"+lastName+"','"+account_Num+"','"+initialDepot+"','"+AccountType+"','"+Phone+"','"+Email+"')";
		
		conn.stm.executeUpdate(querry);
		JOptionPane.showMessageDialog(this, "Customer added seccessfully");
		
		String sql2 ="INSERT INTO balance(customer_ID, Account_Num, Balance) VALUES('"+customer_id+"','"+account_Num+"', '"+initialDepot+"')";
		conn.stm.executeUpdate(sql2);
		
		String sql3 = "CREATE TABLE "+customer_id+" (id INT(100) AUTO_INCREMENT PRIMARY KEY, Transaction_Type VARCHAR(250), Balance VARCHAR(200))";	
		conn.stm.executeUpdate(sql3);
		
		String sql4 = "INSERT INTO "+customer_id+" (Transaction_Type, Balance) VALUES('"+initialD+"', '"+initialDepot+"') ";
		conn.stm.executeUpdate(sql4);
		
		this.dispose();
		
		}
		catch(Exception er) {
			System.out.println(er);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelBtn) {
			this.dispose();
		}
		if(e.getSource()==okBtn) {						
			addCustomer();
						
		}
	}
}






