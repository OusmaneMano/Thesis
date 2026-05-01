package defaut;



import javax.crypto.Cipher;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.swing.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class passwordPage extends JFrame implements ActionListener{
	JPanel panel1, panel2;
	JLabel usernameLabel, passLabel;
	JTextField usernameField, passField;
	JButton loginBtn, cancelBtn;
	PreparedStatement prestm;
	 public static final long EXPIRATION_DURATION_MINUTES = 1;
	 public static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	 
	 int otpValue;
	 SecureRandom random;
	 String otp;

	passwordPage(){
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
		passLabel = new JLabel("Password");
		
		usernameField = new JTextField();
		passField = new JTextField();
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(120,120,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(240,120,80,30);
		
		loginBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		panel1.add(usernameLabel);
		panel1.add(passLabel);
		
		panel2.add(usernameField);
		panel2.add(passField);
		
		cont.add(panel1);
		cont.add(panel2);
		cont.add(loginBtn);
		cont.add(cancelBtn);
		
		this.setLocationRelativeTo(null);
		this.setBounds(950,10,360,220);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
					
	}

	public static void main(String[] args) {
		passwordPage passwordPage = new passwordPage();
		

	}
	
	public void successConnection() {
	    LoginOTP loginOTP = new LoginOTP();

	    Connect conn = new Connect();    
	    String otpMessage = loginOTP.otpField.getText();

	    try {
	        String sql = "SELECT * FROM otps";
	        prestm = conn.connectio.prepareStatement(sql);
	        ResultSet rs = prestm.executeQuery();

	        if (rs.next()) {
	            // Initialize encryption cipher

	            String encryptedOTP = rs.getString("otp");               

	                System.out.println("Decrypted OTP: " + encryptedOTP);
	                // Compare decrypted OTP with entered OTP message
	                
	                    loginOTP.ViewInfo();
	                }             
	         else {
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

    private void scheduleOtpCleanup() {
        executorService.schedule(() -> deleteExpiredOtps(), EXPIRATION_DURATION_MINUTES, TimeUnit.MINUTES);
    }

    private void deleteExpiredOtps() {
        try {
        	  Connect conn = new Connect();
              String sql = "DELETE FROM otps WHERE creation_time < ?";
              try (PreparedStatement pstmt = conn.connectio.prepareStatement(sql)) {
                  // Calculate expiration time (current time - 1 minutes)
                  Instant expirationTime = Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(EXPIRATION_DURATION_MINUTES));
                  pstmt.setTimestamp(1, Timestamp.from(expirationTime));
                  int rowsAffected = pstmt.executeUpdate();
                  System.out.println("Deleted " + rowsAffected + " expired OTP(s) from the database.");
              }
          } catch (SQLException e) {
              System.out.println("Error deleting expired OTPs from database: " + e.getMessage());
          }
    }
    
    public void generateOtp(JTextField usernameField) {
        try {
        	 AES aes = new AES();
             aes.initFromString("icBUI0jyviHwBTpcHs0i7A==", "hscaSRL/mJExm2ix");
                       
             // Generate OTP
            random = new SecureRandom(); 
            otpValue = random.nextInt(9000) + 1000;
            otp = String.valueOf(otpValue);
           
            String encryptedOTP = aes.encrypt(otp);

            Connect conn = new Connect();
            String sql1 = "SELECT * FROM otps WHERE customer_ID = ?";
            prestm = conn.connectio.prepareStatement(sql1);
            prestm.setString(1, usernameField.getText());
            ResultSet rs = prestm.executeQuery();

            if (rs.next()) {
                // Update existing OTP in the database
                String sql2 = "UPDATE otps SET otp = ?, creation_time = ? WHERE customer_ID = ?";
                prestm = conn.connectio.prepareStatement(sql2);
                prestm.setString(1, encryptedOTP);
                prestm.setTimestamp(2, Timestamp.from(Instant.now()));
                prestm.setString(3, usernameField.getText());
                prestm.executeUpdate();
            } else {
                // Insert new OTP into the database
                String sql3 = "INSERT INTO otps(customer_ID, otp, creation_time) VALUES (?, ?, ?)";
                prestm = conn.connectio.prepareStatement(sql3);
                prestm.setString(1, usernameField.getText());
                prestm.setString(2, encryptedOTP);
                prestm.setTimestamp(3, Timestamp.from(Instant.now()));
                prestm.executeUpdate();
            }

            receiveOTPPage receiveOTPPage = new receiveOTPPage();
            receiveOTPPage.otpField.setText(otp);
            scheduleOtpCleanup();
         
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public void secondAuth() {
        Connect conn = new Connect();

        String userN = usernameField.getText();
        String pass = passField.getText();

        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            prestm = conn.connectio.prepareStatement(sql);

            prestm.setString(1, userN);
            prestm.setString(2, pass);

            ResultSet rs = prestm.executeQuery();

            if (rs.next()) {
                generateOtp(usernameField); 
                LoginOTP loginOTP = new LoginOTP();
                loginOTP.usernameField.setText(usernameField.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Credentials");
            }
        } catch (Exception SeE) {
            SeE.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginBtn) {
			secondAuth();
			this.dispose();
			
			//otpMenu otpMenu = new otpMenu();
		}
		else if(e.getSource()==cancelBtn) {
			this.dispose();
		}
		
	}

}




