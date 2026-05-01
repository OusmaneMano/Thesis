package defaut;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class withdrawalPage extends JFrame implements ActionListener{
	JPanel panel = new JPanel();
	JLabel withdraw;
	JTextField withdrawField, Account_NumField;
	JButton okBtn, cancelBtn;
	PreparedStatement prestm;
	
	withdrawalPage(){
		
		this.setTitle("Withdrawal Menu");
		panel = new JPanel();
		panel.setLayout(null);
		
		withdraw = new JLabel("withdrawal Amount");
		withdraw.setBounds(40,70,100,30);
		
		withdrawField = new JTextField(20);
		withdrawField.setBounds(140,70,100,30);
		
		Account_NumField = new JTextField();
		Account_NumField.setBounds(40,20,200,30);
		Account_NumField.setEditable(false);

		
		okBtn = new JButton("OK");
		okBtn.setBounds(40,120,80,30);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(140,120,100,30);
		
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		panel.add(Account_NumField);
		panel.add(withdraw);
		panel.add(withdrawField);
		panel.add(okBtn);
		panel.add(cancelBtn);
		
		this.add(panel);
				
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(800,0,300,200);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		withdrawalPage withdrawalPage = new withdrawalPage();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelBtn) {
			this.dispose();
		}
		if(e.getSource()==okBtn) {
			int withdraw = Integer.parseInt(withdrawField.getText());
			int Account_N = Integer.parseInt(Account_NumField.getText());
			
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
					 JOptionPane.showMessageDialog(null, "Account not found");
				 }
				 int updatedBalance = currentBalance - withdraw;
				 String sql2 = "UPDATE customers SET Amount = ? Where Account_Num = ?";
				 prestm = conn.connectio.prepareStatement(sql2);
				 prestm.setInt(1, updatedBalance);
				 prestm.setInt(2, Account_N);
				 
				 int rowAffected = prestm.executeUpdate();
				 
				 if(rowAffected > 0) {
					 JOptionPane.showMessageDialog(null, "Withdrawal Successfully");
						this.dispose();

				 }
				 else {
					 JOptionPane.showMessageDialog(null, "Withdrawal failed");
						this.dispose();


				 }
				 
				
				
			}
			catch(Exception WithErr) {
				System.out.println(e);
				
			}
		}
		
	}

}
















