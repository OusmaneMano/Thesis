package defaut;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.*;

public class mainPage extends JFrame implements ActionListener{
	JPanel panel1, panel2, panel3;
	JLabel field1, fnameLabel, lnameLabel, accountNumLabel, balanceLabel;
	JButton listBtn, addBtn, remBtn, depBtn, witBtn;
	JTable table;
	DefaultTableModel model;
	JScrollPane pane;
	PreparedStatement prestm;

		
	mainPage(){
		Container cont = this.getContentPane();
		cont.setLayout(null);
		 panel1 = new JPanel();
		 panel1.setBounds(10,10,560,40);
		 //panel1.setBackground(Color.CYAN);
		 panel2 = new JPanel();
		 panel2.setBounds(3,60,577,40);
		 panel2.setBackground(Color.BLUE);
		 panel3 = new JPanel();
		 panel3.setBounds(10,110,560,230);
		 panel3.setBackground(Color.LIGHT_GRAY);
		 //panel3.setLayout(new GridLayout(1,4));

		 field1 = new JLabel("Bank Application");
		 //field1.setBackground(Color.BLUE);
		 field1.setSize(80, 500);
		 
		 listBtn = new JButton("Customer List");
		 addBtn = new JButton("Add Account");
		 remBtn = new JButton("Remove Account");
		 depBtn = new JButton("Deposit");
		 witBtn = new JButton("Withdrawal");
		 
		remBtn.setEnabled(false);
		depBtn.setEnabled(false);
		witBtn.setEnabled(false);
		 
		 table = new JTable();
		 Object [] columns= {"First Name", "Last Name", "Account N°", "Balance"};
		 model = new DefaultTableModel();
		 model.setColumnIdentifiers(columns);
		 table.setModel(model);
		 //table.setAutoCreateRowSorter(true);
		 
		 pane = new JScrollPane(table);
		 	
		panel1.add(field1);
		
		panel2.add(listBtn);
		panel2.add(addBtn);
		panel2.add(remBtn);
		panel2.add(depBtn);
		panel2.add(witBtn);
		
		panel3.add(pane);
		panel3.setVisible(false);
		/*
		 * panel3.add(lnameLabel);
		panel3.add(accountNumLabel);
		panel3.add(balanceLabel);
		*/
		listBtn.addActionListener(this);
		addBtn.addActionListener(this);
		depBtn.addActionListener(this);
		remBtn.addActionListener(this);
		witBtn.addActionListener(this);
				 
		cont.add(panel1);
		cont.add(panel2);
		cont.add(panel3);
		
		this.setResizable(false);
		this.setBounds(750,0,600,400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void listCustomers() {
		
		Connect conn = new Connect();
		try {
			String sql = "SELECT First_Name, Last_Name, Account_Num, Amount "
					+ "FROM customers";
			
			ResultSet rs = conn.stm.executeQuery(sql);
			
			String fName, lName,accountNum, balance;
			while(rs.next()) {
				fName = rs.getString(1);
				lName = rs.getString(2);
				accountNum = rs.getString(3);
				balance = rs.getString(4);
				String [] rows = {fName, lName, accountNum, balance};
			
			model.addRow(rows);
			}
			//selectedRow();
		}
		catch(Exception listE) {
			System.out.println(listE);
			}
	
	}
	public void selectedRow() {
		addAccountPage addA = new addAccountPage();
		addA.setVisible(false);
		int i = table.getSelectedRow();
		if (i >= 0) {
			model.setValueAt(addA.fnameField.getText(), i, 0);
			model.setValueAt(addA.lnameField.getText(), i, 1);
			model.setValueAt(addA.account_NumField.getText(), i, 2);
			model.setValueAt(addA.initialDepField.getText(), i, 3);

		}
		
	}
	

	public static void main(String[] args) {
		mainPage mainPage = new mainPage();	

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== addBtn) {
			new addAccountPage();

		}
		
		if(e.getSource()==listBtn) {
			panel3.setVisible(true);
			listCustomers();
			listBtn.setEnabled(false);
			
			}
		if(e.getSource()==depBtn) {
			depositPage depositPage =new depositPage();
			int i = table.getSelectedRow();
			depositPage.Account_NumField.setText(model.getValueAt(i, 2).toString());
		
			
		}
		if(e.getSource()==witBtn) {
			withdrawalPage withdrawalPage = new withdrawalPage();
			int i = table.getSelectedRow();
			withdrawalPage.Account_NumField.setText(model.getValueAt(i, 2).toString());

		}
		if(e.getSource()==remBtn) {
			int i = table.getSelectedRow();
			model.removeRow(i);
		}
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent em) {
				if(em.getClickCount()==1) {			
					remBtn.setEnabled(true);
					depBtn.setEnabled(true);
					witBtn.setEnabled(true);
					
				}			
			}
		});
}
		public void removeCustomer() {
			Connect conn = new Connect();
			int i = table.getSelectedRow();	
			
			try {
				String sql = "DELETE * FROM customers WHERE Account_Num =?";
				prestm = conn.connectio.prepareStatement(sql);
				prestm.setInt(i, i);				
			}
			catch(Exception remE) {
				System.out.println(remE);
			}
			
		}

}










