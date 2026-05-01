package defaut;

public class Account {
	private double Balance = 0;
	private double Interest = 0.02;
	private int accountNumber;
	private static int numberOfAccount = 1000;


	
	Account(){
		accountNumber = numberOfAccount++;	
	}
	public double getBalance() {
		return Balance;	
	}
	public void setBalance(double Balance) {
		this.Balance = Balance;
	}
	
	public double getInterest() {
		return Interest;
	}
	public void setInterest(double Interest) {
		this.Interest = Interest*100;
	}
	public int getaccountNumber() {
		return accountNumber;
	}
	
	public void withdraw(double amount) {
		if((Balance + 5) < amount) {
			System.out.println("Insifficient balance");
			return;
		}
		else {
			Balance -= amount +5;
			checkInterest(0);
			System.out.println("You have withdrawed an amount of "+amount+ " Euros inoccured an interest of "+Interest+"%");
			System.out.println("You now have a balance of " + Balance+ " Euros");

		}
	}
	public void deposit(double amount) {
		if(Balance <= 0) {
			System.out.println("You can not deposit negative money");
		}
		else {
			checkInterest(amount);
			Balance += amount + (amount*Interest);
			System.out.println("You have deposited an amount of "+amount+ " Euros with an interest rate of "+Interest*100+"%");
			System.out.println("You now have a balance of " + Balance+ " Euros");
		
		}
	}
	public void checkInterest(double amount) {
		if(Balance + amount > 10000) {
			Interest = 0.05;		
		}
		else {
			Interest = 0.02;
		}
		
	}
	
}
