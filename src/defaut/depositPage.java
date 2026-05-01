package defaut;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class depositPage extends JFrame implements ActionListener{
	JPanel panel;
	JLabel deposit;
	JTextField depositField, Account_NumField;
	JButton okBtn, cancelBtn;
	JTable table;
	DefaultTableModel model;
	JScrollPane pane;
	PreparedStatement prestm;
	
	depositPage(){			
		this.setTitle("Deposit Menu");
		panel = new JPanel();
		panel.setLayout(null);
		
		Account_NumField = new JTextField();
		Account_NumField.setBounds(40,20,200,30);
		Account_NumField.setEditable(false);
		
		deposit = new JLabel("Deposit Amount");
		deposit.setBounds(40,70,100,30);
		
		depositField = new JTextField(20);
		depositField.setBounds(140,70,100,30);
		
		okBtn = new JButton("OK");
		okBtn.setBounds(40,120,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(140,120,100,30);
		
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		
		panel.add(Account_NumField);
		panel.add(deposit);
		panel.add(depositField);
		panel.add(okBtn);
		panel.add(cancelBtn);
		
		this.add(panel);
				
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(800,0,300,200);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		depositPage depositPage = new depositPage();
	}
	
	public void depositMethode() {
		int deposit = Integer.parseInt(depositField.getText());
		int account_N = Integer.parseInt(Account_NumField.getText());
		
		try {
			Connect conn = new Connect();
			
			String sql = "SELECT Amount FROM customers Where Account_Num = ?";
			
			prestm = conn.connectio.prepareStatement(sql);
			prestm.setLong(1, account_N);
							
			ResultSet rs = prestm.executeQuery();
			
			int currentBalance = 0;
			if (rs.next()) {
				currentBalance = rs.getInt("Amount");
			}
			else {
				JOptionPane.showMessageDialog(null, "Account not found");
			}
			int updatedBalance = currentBalance + deposit;
			String sql2 = "Update customers set Amount = ?  Where Account_Num = ? ";
			prestm = conn.connectio.prepareStatement(sql2);
			prestm.setInt(1, updatedBalance);
			prestm.setLong(2, account_N);
			
			int rowAffected = prestm.executeUpdate();
			
			if(rowAffected > 0) {
				//JOptionPane.showMessageDialog(null, "Successful");
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Failed");
				this.dispose();
			}			
		}
		catch(Exception depE) {
			System.out.println(depE);
		}
	}
	
	public void viewInfoDeposit() {
		int Account_N = Integer.parseInt(Account_NumField.getText());
		
		try {
			Connect conn = new Connect();
			String sql = "Select * FROM customers WHERE Account_Num = ?";
			prestm = conn.connectio.prepareStatement(sql);
			prestm.setInt(1, Account_N);
			
			ResultSet rs = prestm.executeQuery();
			
			if(rs.next()) {					
				
				otpMenu otpMenu = new otpMenu();
				
				otpMenu.nameLabel.setText(rs.getString("First_Name")+" "+rs.getString("Last_Name"));
				otpMenu.acount_NumLabel.setText(rs.getString("Account_Num"));
				otpMenu.depositAmountLabel.setText(depositField.getText());
				this.dispose();
		
			}
			else {
				JOptionPane.showMessageDialog(null, "Account not Found");
			}			
		}
		catch(Exception depE) {
			System.out.println(depE);
		}
		
	}
	public void deposit() {
		String depositStr = depositField.getText();
		int Account_N = Integer.parseInt(Account_NumField.getText());
		
		try {
			 // Check if the input is numeric and non-negative
	        if (isValidDeposit(depositStr)) {
	            // Convert the deposit amount to an integer
	            int deposit = Integer.parseInt(depositStr);
	            // Proceed with the deposit operation
			Connect conn = new Connect();
			String sql = "SELECT Amount FROM customers WHERE Account_Num = ?";
			prestm = conn.connectio.prepareStatement(sql);
			prestm.setInt(1, Account_N);
			
			ResultSet rs = prestm.executeQuery();
			
			int currentBalance = 0;
			if(rs.next()) {
				currentBalance = rs.getInt("Amount");
			}
			else {
				JOptionPane.showMessageDialog(null, "Account not Fount");
			}
			
			int updateBalance = currentBalance + deposit;
			
			String sql2 = "UPDATE customers SET Amount = ? WHERE Account_Num = ?";
			prestm = conn.connectio.prepareStatement(sql2);
			prestm.setInt(1, updateBalance);
			prestm.setInt(2, Account_N);			
			
			int rowAffected = prestm.executeUpdate();
			
			String sql3 = "UPDATE balance SET balance = ? WHERE Account_Num = ?";
			prestm = conn.connectio.prepareStatement(sql3);
			prestm.setInt(1, updateBalance);
			prestm.setInt(2, Account_N);
			
			int rowAffected2 = prestm.executeUpdate();
			
			if(rowAffected2 > 0 && rowAffected > 0 ) {				
				JOptionPane.showMessageDialog(null, "Balance Updated");
				this.dispose();	
			}
			else {
				JOptionPane.showMessageDialog(null, "Deposit Failed");
			}
			
		}
		 else {
            // Display an error message if the input is invalid
            JOptionPane.showMessageDialog(null, "Invalid deposit amount. Please enter a positive integer.");
        }
		}
		catch(Exception otpEr) {
			System.out.println(otpEr);
		}
		
	}

	// Method to validate the deposit amount
	private boolean isValidDeposit(String depositStr) {
	    // Check if the input string is empty
	    if (depositStr.isEmpty()) {
	        return false;
	    }
	    
	    // Check if the input string contains only digits
	    for (char c : depositStr.toCharArray()) {
	        if (!Character.isDigit(c)) {
	            return false;
	        }
	    }
	    
	    // Check if the input string represents a non-negative integer
	    int deposit = Integer.parseInt(depositStr);
	    return deposit >= 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource()==cancelBtn) {
			this.dispose();
		}
		if(e.getSource()==okBtn) {
			deposit();
			
		}
		
	}

}








