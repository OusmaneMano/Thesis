package defaut;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class otpMenu extends JFrame implements ActionListener{
	JPanel panel = new JPanel();
	JLabel otp, name, account_Num, nameLabel, acount_NumLabel, depositAmount, depositAmountLabel, usernameLabel;
	JTextField otpField;
	JButton confirmBtn, cancelBtn;
	JTable table;
	DefaultTableModel model;
	JScrollPane pane;
	PreparedStatement prestm;
	
	otpMenu(){
		this.setTitle("Enter The OTP Message");
		panel = new JPanel();
		panel.setLayout(null);
		
		name = new JLabel("Name: ");
		name.setBounds(40, 10, 100, 30);
		
		nameLabel = new JLabel("--------------------------------");
		nameLabel.setBounds(100, 10, 150, 30);
		
		account_Num = new JLabel("Account N°: ");
		account_Num.setBounds(40, 50, 100, 30);

		acount_NumLabel = new JLabel("---------------------------");
		acount_NumLabel.setBounds(120, 50, 150, 30);
		
		depositAmount = new JLabel("Deposit Amount: ");
		depositAmount.setBounds(40, 90, 100, 30);
		
		depositAmountLabel = new JLabel("----------------------");
		depositAmountLabel.setBounds(140, 90, 150, 30);
		
		usernameLabel = new JLabel("username");
		usernameLabel.setBounds(40, 130, 150, 30);
		//usernameLabel.setVisible(false);
	
		otp = new JLabel("OTP Message");
		otp.setBounds(40,190,100,30);
		
		otpField = new JTextField(20);
		otpField.setBounds(140,190,100,30);
		
		confirmBtn = new JButton("Confirm");
		confirmBtn.setBounds(40,240,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(140,240,100,30);
		
		confirmBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		panel.add(otp);
		panel.add(otpField);
		panel.add(confirmBtn);
		panel.add(cancelBtn);
		panel.add(name);
		panel.add(nameLabel);
		panel.add(account_Num);
		panel.add(acount_NumLabel);
		panel.add(usernameLabel);
		panel.add(depositAmount);
		panel.add(depositAmountLabel);
		
		this.add(panel);
				
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(800,0,300,350);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		otpMenu otpMenu = new otpMenu();
		
	}
	
	public void deposit() {
		int deposit = Integer.parseInt(depositAmountLabel.getText());
		int Account_N = Integer.parseInt(acount_NumLabel.getText());
		
		try {
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
		catch(Exception otpEr) {
			System.out.println(otpEr);
		}
		
	}
	
	public void successDeposit() {
		
		 Connect conn = new Connect();    
		    String otpMessage = otpField.getText();
		    String username = usernameLabel.getText();
		    
		    try {
	            AES aes = new AES();
	            aes.initFromString("icBUI0jyviHwBTpcHs0i7A==", "hscaSRL/mJExm2ix");
					    	
	            String sql = "SELECT * FROM balance WHERE customer_ID = ?";
				prestm = conn.connectio.prepareStatement(sql);
				prestm.setString(1, username);
		        ResultSet rs = prestm.executeQuery();
		        
		        if (rs.next()) {

		            String encryptedOTP = rs.getString("otp");
		           // System.out.println("Encrypted OTP: "+encryptedOTP);
		            String decryptedOTP = aes.decrypt(encryptedOTP);
		           // System.out.println("Well done: "+ decryptedOTP);
 
		           if (decryptedOTP.equals(otpMessage)) {           
		                deposit();
		            } else {
		                JOptionPane.showMessageDialog(null, "Incorrect OTP");     
		        }
		           } else {
		            JOptionPane.showMessageDialog(null, "OTP not found in database");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error during OTP decryption");
		    } finally {
		        try {
		            if (prestm != null) {
		                prestm.close();
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==confirmBtn) {		
			deposit();
		
		}
		else if(e.getSource()==cancelBtn) {
			this.dispose();
		}		
	}

}














