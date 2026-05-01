package defaut;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connect {
	String server = "jdbc:mysql://localhost/";
	String dbName = "bankapp";
	String username = "root";
	String pass = "";
	Connection connectio;
	Statement stm;
	
	Connect(){
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connectio = DriverManager.getConnection(server+dbName,username,pass);
			stm = connectio.createStatement();
			
			//System.out.println("connect Succesfully");
		}
		catch(Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		
	}
	
		
	public static void main(String[] args) {
		//Connect Con = new Connect();
	}

}
