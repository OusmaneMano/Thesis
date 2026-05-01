package defaut;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.crypto.SecretKey;
import javax.swing.*;

public class mobileApp extends JFrame implements ActionListener{
	JPanel panel;
	JLabel label1, label2, label3, label4;
	JButton btn1, btn2;
	PreparedStatement prestm;
	
	SecretKey key;
	
	mobileApp(){
		setTitle("Bank App");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		label4 = new JLabel("user");
		label4.setBounds(220, 0, 200, 30 );
		
		label1 = new JLabel("Account Num");
		label1.setBounds(50, 30, 200, 30 );
		
		label2 = new JLabel("BALANCE");
		label2.setBounds(50, 90, 200, 30 );
				
		label3 = new JLabel("Ammount");
		label3.setBounds(50, 120, 200, 30 );
		
		btn1 = new JButton("Transfer");
		btn1.setBounds(50, 200, 90,30);
		btn2 = new JButton("Close");
		btn2.setBounds(150, 200, 80,30);
		
		panel.add(label4);
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(btn1);
		panel.add(btn2);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);	

		this.add(panel);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(800,0,300,350);
		this.setVisible(true);
		
	}

	public static void main(String[] args) {

		mobileApp mobileApp = new mobileApp();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			depositPage depositPage = new depositPage();
			depositPage.Account_NumField.setText(label1.getText());
			
		}
		if(e.getSource()==btn2) {
			this.dispose();
		}
		
	}

}







