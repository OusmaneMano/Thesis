package defaut;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class receiveOTPPage extends JFrame implements ActionListener{
	
	JPanel panel;
	JLabel otp;
	JTextField otpField;
	JButton confirmBtn, cancelBtn;
	PreparedStatement prestm;

	
	receiveOTPPage(){
		this.setTitle("The received OTP Message ");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		otp = new JLabel("Received OTP:");
		otp.setBounds(40,105,100,30);
		
		otpField = new JTextField("otp");
		otpField.setBounds(140,105,100,30);
		
		confirmBtn = new JButton("Confirm");
		confirmBtn.setBounds(40,155,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(140,155,100,30);
		
		confirmBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		panel.add(otp);
		panel.add(otpField);
		panel.add(confirmBtn);
		panel.add(cancelBtn);
		
		this.add(panel);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(0,350,300,350);
		this.setVisible(true);	
		
	}

	public static void main(String[] args) {
		receiveOTPPage receiveOTPPage = new receiveOTPPage();
		
	}
	
	public void deleteOTP() {
		Connect conn = new Connect();
		
		try {
			String sql = "DELETE FROM otps";
			prestm = conn.connectio.prepareStatement(sql);
			
			int rs = prestm.executeUpdate();
			
				
			if(rs > 0) {
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Problem");

			}
			
		}
		catch(Exception deError) {
			deError.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()== confirmBtn) {
			deleteOTP();
			this.dispose();
		}
	}

}












