package defaut;

public class Checking extends Account {
	private String accountType = "Checking";
	
	Checking(double initialDeposit){
		super();
		this.setBalance(initialDeposit);
		this.checkInterest(0);

	}
	
	@Override
	public String toString() {
		return "Account Type: "+accountType+ " account\n"+
				"Account Number: "+this.getaccountNumber()+"\n"+
				"Balance: "+this.getBalance()+"\n"+
				"Interest: "+this.getInterest()+"%\n";
	}
	
}
