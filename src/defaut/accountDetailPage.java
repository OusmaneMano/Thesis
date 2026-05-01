package defaut;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.*;

public class accountDetailPage extends JFrame{
	JPanel panel1, panel2;
	JLabel fnameLabel, lnameLabel, ssnLabel, AccountTypeLabel, AccountNumLabel, phoneLabel, 
				emailLabel, balanceLabel, InterestRateLabel, transactionFeeLabel;
	JTextField fnameField, lnameField, ssnField, AccountTypeField, AccountNumField, phoneField, 
				emailField, balanceField, InterestRateField, transactionFeeField;
	JButton okBtn;
	
	accountDetailPage(){
		Container cont = this.getContentPane();
		cont.setLayout(null);
		
		this.setTitle("Acoount Details");
		
		panel1 = new JPanel();
		panel1.setBounds(40,10,180,400);
		panel1.setBackground(Color.lightGray);
		panel1.setLayout(new GridLayout(10,1));
		panel2 = new JPanel();
		panel2.setBounds(240,10,200,400);
		panel2.setBackground(Color.lightGray);
		panel2.setLayout(new GridLayout(10,1,10,10));
		
		fnameLabel = new JLabel("First Name");
		lnameLabel = new JLabel("Last Name");
		ssnLabel = new JLabel("Social Security Number");
		AccountTypeLabel = new JLabel("Account Type");
		AccountNumLabel = new JLabel("Account Number");
		phoneLabel = new JLabel("Phone N°");
		emailLabel = new JLabel("Email");
		balanceLabel = new JLabel("Balance");
		InterestRateLabel = new JLabel("Interest Rate");
		transactionFeeLabel = new JLabel("Transaction Fee");
		
		fnameField = new JTextField();
		fnameField.setEditable(false);
		lnameField = new JTextField();
		lnameField.setEditable(false);
		ssnField = new JTextField();
		ssnField.setEditable(false);
		AccountTypeField = new JTextField();
		AccountTypeField.setEditable(false);
		AccountNumField = new JTextField();
		AccountNumField.setEditable(false);
		phoneField = new JTextField();
		phoneField.setEditable(false);
		emailField = new JTextField();
		emailField.setEditable(false);
		balanceField = new JTextField();
		balanceField.setEditable(false);
		InterestRateField = new JTextField();
		InterestRateField.setEditable(false);
		transactionFeeField = new JTextField();
		transactionFeeField.setEditable(false);
		
		okBtn = new JButton("OK");
		okBtn.setBounds(340, 430, 100, 30);
		
		panel1.add(fnameLabel);
		panel1.add(lnameLabel);
		panel1.add(ssnLabel);
		panel1.add(AccountTypeLabel);
		panel1.add(AccountNumLabel);
		panel1.add(phoneLabel);
		panel1.add(emailLabel);
		panel1.add(balanceLabel);
		panel1.add(InterestRateLabel);
		panel1.add(transactionFeeLabel);

		panel2.add(fnameField);
		panel2.add(lnameField);
		panel2.add(ssnField);
		panel2.add(AccountTypeField);
		panel2.add(AccountNumField);
		panel2.add(phoneField);
		panel2.add(emailField);
		panel2.add(balanceField);
		panel2.add(InterestRateField);
		panel2.add(transactionFeeField);
				
		cont.add(panel1);
		cont.add(panel2);
		cont.add(okBtn);
		
		this.setBounds(700, 0, 500, 520);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		accountDetailPage accountDetailPage = new accountDetailPage();
	}

}
