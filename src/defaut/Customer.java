package defaut;

public class Customer {

	private String fName;
	private String lName;
	private String ssn;
	private Account account;

	public Customer(String fName, String lName, String ssn, Account account) {
		
		this.fName = fName;
		this.lName = lName;
		this.ssn = ssn;
		this.account = account;
	}
	
	@Override
	public String toString() {
		return "\nCustomer Informations:\n"+
				"First Name: "+this.fName+
				"Last Name: "+this.lName+
				"SSN: "+this.ssn+
				account;
}
	public String basicInfo() {
		return  " Account Number: "+ account.getaccountNumber()+" - "+
				"Name: "+this.fName+" "+this.lName;
}
	
	Account getAccount() {
		return account;
	}
}