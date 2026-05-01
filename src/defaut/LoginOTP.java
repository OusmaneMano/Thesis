package defaut;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;


public class LoginOTP extends JFrame implements ActionListener{
	JPanel panel1, panel2;
	JLabel usernameLabel, passLabel;
	JTextField usernameField, otpField;
	JButton loginBtn, cancelBtn;
	PreparedStatement prestm;

	
	LoginOTP(){
		Container cont = this.getContentPane();
		cont.setLayout(null);
		this.setTitle("Login Page");
		
		panel1 = new JPanel();
		panel1.setBounds(20,10,100,100);
		panel1.setLayout(new GridLayout(2,1));
		panel2 = new JPanel();
		panel2.setBounds(120,20,200,80);
		panel2.setLayout(new GridLayout(2,1,10,10));

		usernameLabel = new JLabel("Username");
		passLabel = new JLabel("OTP Message");
		
		usernameField = new JTextField("username");
		usernameField.setEditable(false);
		otpField = new JTextField();
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(120,120,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(240,120,80,30);
		
		loginBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		panel1.add(usernameLabel);
		panel1.add(passLabel);
		
		panel2.add(usernameField);
		panel2.add(otpField);
		
		cont.add(panel1);
		cont.add(panel2);
		cont.add(loginBtn);
		cont.add(cancelBtn);
		
		this.setLocationRelativeTo(null);
		this.setBounds(950,10,360,220);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		ViewInfo();
			
	}

	public static void main(String[] args) {
		LoginOTP loginOTP = new LoginOTP();

	}
	

	public void ViewInfo() {
		Connect conn = new Connect();
		
		String username = usernameField.getText();
				
		try {
						
			String sql = "SELECT * FROM balance WHERE customer_ID = ?";
			prestm = conn.connectio.prepareStatement(sql);
			prestm.setString(1, username);
			ResultSet rs = prestm.executeQuery();
			
			while(rs.next()) {
				mobileApp mobileApp = new mobileApp();
				mobileApp.label1.setText(rs.getString("Account_Num"));
				mobileApp.label3.setText(rs.getString("Balance"));
				mobileApp.label4.setText(rs.getString("customer_ID"));
			}				
		}
		catch(Exception vE) {
			vE.printStackTrace();
		}	
	}
	
	public void successConnection() {
		
		 Connect conn = new Connect();    
		    String otpMessage = otpField.getText();
		    
		    try {
	            AES aes = new AES();
	            aes.initFromString("icBUI0jyviHwBTpcHs0i7A==", "hscaSRL/mJExm2ix");
				String username = usernameField.getText();
	            
		        String sql = "SELECT * FROM otps WHERE customer_ID = ?";
		        prestm = conn.connectio.prepareStatement(sql);
		        prestm.setString(1, username);
		        ResultSet rs = prestm.executeQuery();
		        
		        if (rs.next()) {

		            String encryptedOTP = rs.getString("otp");
		            String decryptedOTP = aes.decrypt(encryptedOTP);
  
		           if (decryptedOTP.equals(otpMessage)) {           
		                ViewInfo();
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

		if(e.getSource()==loginBtn) {
			successConnection();
			this.dispose();

		}
		else if(e.getSource()==cancelBtn) {
			this.dispose();
		}
		
	}

}








