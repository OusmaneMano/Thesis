package defaut;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	
	Scanner scan = new Scanner(System.in);
	Bank bank = new Bank();
	boolean exit;

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.runMenu();
	}
	
	public void runMenu() {
		printHeader();
		while(!exit) {
		printMenu();
		int choice = getInput();
		performAction(choice);
		
		}		
	}

	private void performAction(int choice) {
		switch(choice) {
		case 0:
			System.out.println("Thanks for using our application");
			System.exit(0);
			break;
		case 1:
			CreateAnAccount();
			break;
		case 2:
			MakeDeposit();
			break;
		case 3:
			MakeWithdrawal();
			break;
		case 4:
			ListAccountBalance();
		}	
	}

	private void ListAccountBalance() {
		displayHeader("List account details");
		int account = selectAccount();
		if(account >= 0) {	
			displayHeader("Account details");
		System.out.println(bank.getCustomer(account).getAccount());
	}
	}

	private void MakeWithdrawal() {
		displayHeader("Make a Withdraw");
		int account = selectAccount();
		if(account >= 0) {
		System.out.println("How much would you like to withdraw?: ");
		double amount = 0;
		
		try {
			amount = Double.parseDouble(scan.nextLine());	
		}
		catch(NumberFormatException e) {
			amount = 0;
		}
		bank.getCustomer(account).getAccount().withdraw(amount);
	}		
	}

	private void MakeDeposit() {
		displayHeader("Make a Deposit");
		int account = selectAccount();
		if(account >= 0) {
		System.out.println("How much would you like to deposit?: ");
		double amount = 0;
		
		try {
			amount = Double.parseDouble(scan.nextLine());	
		}
		catch(NumberFormatException e) {
			amount = 0;
		}
		bank.getCustomer(account).getAccount().deposit(amount);
	}
	}

	private int selectAccount() {
		ArrayList<Customer> customers = bank.getCustomers();
		if(customers.size() <= 0) {
			System.out.println("No Customer");
			return -1;
		}
		System.out.println("Select an account");
		for(int i = 0; i < customers.size(); i++) {
			System.out.println((i+1) +") "+customers.get(i).basicInfo());
		}
		int account = 0;
		System.out.print("Please enter your selection: ");
		try {
			account = Integer.parseInt(scan.nextLine()) -1;	
		}
		catch(NumberFormatException e) {
			account = -1;
		}
		if(account < 0 || account > customers.size()) {
			System.out.println("Invalid account selected");
			account = -1;
		}
		
		return account;
	}
	
	private String getAccountType() {
		String accountType = "";
		boolean valid = false;
		while(!valid) {
		
			accountType = askQuestion("Please enter your account type (Checking/Saving)");
			if(accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("saving")) {
				valid = true;
			}
			else {
				System.out.println("Please. Account type may be \"Checking\" or \"saving\"");
			}	
		}
		return accountType;
		
	}
	private String askQuestion(String question) {
		String response = "";
		Scanner input = new Scanner(System.in);
		System.out.println(question);
		response = input.nextLine();
		return response;
	}
	
	private double getDeposit(String accountType) {
		double initialDeposit = 0;
		Boolean valid = false;
		while(!valid) {
			System.out.println("Please enter the initial deposit");
			try {
				initialDeposit = Double.parseDouble(scan.nextLine());				
			}
			catch(NumberFormatException e) {
				System.out.println("Initial deposit must be a number");			
			}
			if(accountType.equalsIgnoreCase("checking")) {
				if(initialDeposit < 100) {
					System.out.println("Checking account requires at least an initial deposit of $100");
				}
			else {
				valid = true;
			}
			}
			if(accountType.equalsIgnoreCase("saving")) {
				if(initialDeposit < 50) {
					System.out.println("saving account requires at least an initial deposit of $50");
				}
			else {
				valid = true;
			}
			}
		}
		return initialDeposit;
	}

	private void CreateAnAccount() {
		displayHeader("Create an Account");
		String fName, lName, ssn;
		String accountType = getAccountType();		
		fName= askQuestion("Please enter your first name");
		lName = askQuestion("Please enter your last name");
		ssn = askQuestion("Please enter your Social Security Number");
		double initialDeposit = getDeposit(accountType);
		
		Account account;
		if(accountType.equalsIgnoreCase("Checking")) {
			account = new Checking(initialDeposit);
		}
		else {
			account = new Saving(initialDeposit);
		}
		Customer customer = new Customer(fName, lName, ssn, account);
		bank.addCustomer(customer);
	}

	private int getInput() {
		int choice = -1;
		
		do {
			System.out.println("Please your choice:");
		try {
			choice = Integer.parseInt(scan.nextLine());	
		}
		catch(NumberFormatException e) {
			System.out.println("Please, The input must be a number.");
		}
		if(choice < 0 || choice > 4) {
			System.out.println("Out of range. please choose again.");
		}
		}
		while(choice < 0 || choice > 4);
		
		return choice;
	}

	private void printMenu() {
		displayHeader("Please make a choice");
		System.out.println("1) Create a new account");
		System.out.println("2) Make a deposit");
		System.out.println("3) Make a withdrawal");
		System.out.println("4) List account Balance");
		System.out.println("0) Exit");	
	}

	private void printHeader() {
		System.out.println("+.......................................+");
		System.out.println("|               Welcome Mr/s            |");
		System.out.println("|             Awesome Bank App          |");
		System.out.println("+.......................................+");
		System.out.println("");
	}
	
	private void displayHeader(String message) {
		System.out.print("");
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		int width = message.length() + 6;
		for(int i = 0; i < width; ++i) {
			sb.append("-");
		}
		sb.append("+");
		System.out.println(sb.toString());
		System.out.println("|   "+message+"   |");
		System.out.println(sb.toString());
	}
}












