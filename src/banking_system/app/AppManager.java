package banking_system.app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import banking_system.banking.Account;
import banking_system.banking.AccountProperties;
import banking_system.banking.Payee;
import banking_system.users.AccountOwner;
import banking_system.users.Credentials;
import banking_system.users.PhoneNumber;

public class AppManager {
	
	private static final int MAX_USERS = 200;
	
	private static AccountOwner currUser;
	private static AccountOwner[] users;
	public static BankManager manager;
	private static int numOfUsers;
	
	protected static Scanner sc = new Scanner(System.in);
	
	public AppManager()
	{
		users = new AccountOwner[MAX_USERS];
		numOfUsers = 0;
		setManager();
		setUsers();
	}
	
	private void addUser(AccountOwner owner)
	{
		users[numOfUsers++] = owner;
	}
	
	private void setManager()
	{
		Credentials managerCred = new Credentials("rotemlevi", "rotem8");
		PhoneNumber managerPhone = new PhoneNumber(52, 5360337);
		LocalDate managerBday = LocalDate.of(1994, 8, 8);
		manager = new BankManager("Rotem", "Levi", managerPhone, managerBday, 50000, managerCred);
		users[numOfUsers++] = manager;
	}
	
	private void setUsers()
	{
		Credentials c1 = new Credentials("yaron", "yaron12");
		LocalDate bday1 = LocalDate.of(1994, 6, 22);
		PhoneNumber phone1 = new PhoneNumber(54,5304014);
		AccountOwner owner1 = new AccountOwner("Yaron", "Shender", phone1, bday1, 20000, c1);
		owner1.setAccount(new Account(AccountProperties.BRONZE)); 
		addUser(owner1);
		
		Credentials c2 = new Credentials("matanl", "matan755");
		LocalDate bday2 = LocalDate.of(2007, 5, 7);
		PhoneNumber phone2 = new PhoneNumber(54,5301114);
		AccountOwner owner2 = new AccountOwner("Matan", "Levi", phone2, bday2, 20000, c2);
		owner2.setAccount(new Account(AccountProperties.GOLD));
		addUser(owner2);
		
	}
	
	public AccountOwner login(String username, String password)
	{
		
		AccountOwner loggingOwner = null;
		Credentials ownerCredentials = null;
		for (int i=0; i<users.length && loggingOwner == null; i++)
		{
			Credentials currentCredentials = users[i].getCredentials();
			if (currentCredentials.getUsername().equals(username))
			{
				loggingOwner = users[i];
				ownerCredentials = currentCredentials;
			}
		}
		if (loggingOwner == null)
		{
			System.out.println("No account owner with the given username.");
			return null;
		}
		if (ownerCredentials.getPassword().equals(password))
		{
			System.out.println("Successfully logged in.");
			return loggingOwner;
		}
		int tries = 3;
		while (tries > 0)
		{
			
			System.out.printf("Wrong password! you have %d more tries\n", tries);
			System.out.println("Enter password");
			String currentTry = sc.next();
			if (ownerCredentials.getPassword().equals(currentTry))
			{
				System.out.println("Successfully logged in.");
				return loggingOwner;
			} 
			else
			{
				tries--;
				
			}
		}
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Your account has been blocked, come back at "+now.plusMinutes(30));
		return null;
	}
	
	public AccountOwner login(PhoneNumber phoneNum)
	{
		AccountOwner owner = getOwnerByPhoneNum(phoneNum);
		if (owner == null)
		{
			System.out.println("No user with the given phone number.");
			return null;
		}
		System.out.println("Successfully logged in.");
		return owner;
	}
	
	public static AccountOwner getOwnerByPhoneNum(PhoneNumber phoneNum)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getPhoneNum().equals(phoneNum))
				return users[i];
		}
		return null;
	}
	
	public void logout()
	{
		System.out.println("Successfully logged out.");
		currUser = null;
	}
	
	public static boolean doesUsernameExist(String username)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getCredentials().getUsername().equals(username))
				return true;
		}
		return false;
	}
	
	public void openAccount()
	{
		System.out.println("Enter phone number area code");
		int areaCode = sc.nextInt();
		System.out.println("Enter phone number");
		int phoneNum = sc.nextInt();
		PhoneNumber newPhone = new PhoneNumber(areaCode, phoneNum);
		if (getOwnerByPhoneNum(newPhone) != null)
		{
			System.out.println("There is already an account with this phone number. please login or register with a different number");
			return;
		}
		sc.nextLine();
		System.out.println("Enter your first name");
		String name = sc.next();
		System.out.println("Enter your last name");
		String lastName = sc.next();
		System.out.println("Enter year of birth");
		int year = sc.nextInt();
		System.out.println("Enter month of birth (1-12)");
		int month = sc.nextInt();
		System.out.println("Enter day of birth (1-31)");
		int day = sc.nextInt();
		sc.nextLine();
		LocalDate birthDate = LocalDate.of(year, month, day);
		System.out.println("Enter username: letters and digits only");
		String username = sc.next();
		if (doesUsernameExist(username))
		{
			System.out.println("Username already exists in the system. please login");
			return;
		}
		while (!Credentials.isUsernameLegal(username))
		{
			System.out.println("Invalid username, please enter a username according to the instructions");
			username = sc.next();
		}
		System.out.println("Please enter password: 4-8 characters, must contain a digit and a letter");
		String password = sc.next();
		while (!Credentials.isPasswordLegal(password))
		{
			System.out.println("Invalid password, please enter a password according to the instructions");
			password = sc.next();
		}
		Credentials newCred = new Credentials(username, password);
		System.out.println("Please enter your monthly income");
		double income = sc.nextDouble();
		AccountOwner newOwner = new AccountOwner(name, lastName, newPhone, birthDate, income, newCred);
		addUser(newOwner);
		manager.addUserToApprove(newOwner);
		System.out.println("Application completed. waiting for managar approval and setting.");
		
	}
	
	public void run()
	{
		//TODO
		System.out.println("Welcome to the AJBC Bank!");
		userMenu();
		
	}
	
	public void printUserOptions()
	{
		System.out.println("\nTo open account, enter 1");
		System.out.println("To login with username and password, enter 2");
		System.out.println("To login with phone number, enter 3");
		System.out.println("To exit the system, enter -1");
	}
	
	public void userMenu()
	{
		printUserOptions();
		int choice = sc.nextInt();
		sc.nextLine();
		int areaCode, number;
		String username, password;
		while (choice != -1)
		{
			switch (choice)
			{
			case 1:
				openAccount();
				break;
			case 2:
				System.out.println("Enter username");
				username = sc.next();
				System.out.println("Enter password");
				password = sc.next();
				currUser = login(username, password);
				if (currUser != null)
					if (currUser instanceof BankManager)
						managerMenu();
					else
						accountOwnerMenu();
				break;
			case 3:
				System.out.println("Enter phone area code");
				areaCode = sc.nextInt();
				System.out.println("Enter phone number");
				number = sc.nextInt();
				PhoneNumber currPhone = new PhoneNumber(areaCode, number);
				currUser = login(currPhone);
				if (currUser != null)
					if (currUser instanceof BankManager)
						managerMenu();
					else
						accountOwnerMenu();
				break;
			default:
				break;
			}
			printUserOptions();
			choice = sc.nextInt();
			sc.nextLine();
		}
		System.out.println("Goodbye!");
	}
	
	public void accountOwnerMenu()
	{
		printOwnerOptions();
		int choice = sc.nextInt();
		int year, month, day, areaCode, number;
		double amount;
		PhoneNumber phoneNum;
		while (choice != -1)
		{
			switch (choice)
			{
			case 1:
				currUser.checkBalance();
				break;
			case 2:
				System.out.println("Enter start date for report: year, month and day of month");
				year = sc.nextInt();
				month = sc.nextInt();
				day = sc.nextInt();
				LocalDate start = LocalDate.of(year, month, day);
				currUser.produceReport(start);
				break;
			case 3:
				System.out.println("Your authentication code is "+getAuthenticationCode());
				System.out.println("Enter amount for deposit");
				amount = sc.nextDouble();
				currUser.deposit(amount);
				break;
			case 4:
				System.out.println("Enter amount for withdrawal");
				amount = sc.nextDouble();
				currUser.withdrawl(amount);
				break;
			case 5:
				System.out.println("Enter phone number of receiver: area code and number");
				areaCode = sc.nextInt();
				number = sc.nextInt();
				phoneNum = new PhoneNumber(areaCode, number);
				AccountOwner receiver = getOwnerByPhoneNum(phoneNum);
				if (receiver == null)
				{
					System.out.println("No user with given phone number, operation terminates.");
					break;
				}
				System.out.println("Enter amount to transfer");
				amount = sc.nextDouble();
				currUser.transferFunds(amount, receiver);
				break;
			case 6:
				Payee payee = getPayee();
				System.out.println("Enter amount to pay");
				amount = sc.nextDouble();
				currUser.payBill(amount, payee);
				break;
			case 7: 
				System.out.println("Enter amount to loan");
				amount = sc.nextDouble();
				System.out.println("Enter number of monthly payments");
				int months = sc.nextInt();
				currUser.getLoan(amount, months);
				break;
			default:
				break;
			}
			printOwnerOptions();
			choice = sc.nextInt();
		}
		
	}
	
	public void managerMenu()
	{

		int choice;
		printManagerOptions();
		choice = sc.nextInt();
		while (choice != -1)
		{
			if (choice == 1)
				manager.setAndApproveAcc();
			else
				accountOwnerMenu();
			printManagerOptions();
			choice = sc.nextInt();
		}

	}
	
	public void printManagerOptions()
	{
		System.out.println("To approve and set accounts, enter 1");
		System.out.println("To owner menu, enter 2");
		System.out.println("To main menu, enter -1");
	}
	
	public void printOwnerOptions()
	{
		System.out.println("\nTo check your balance, enter 1");
		System.out.println("To produce an activity report, enter 2");
		System.out.println("To make a deposit, enter 3");
		System.out.println("To make a withdrawal, enter 4");
		System.out.println("To transfer funds, enter 5");
		System.out.println("To pay a bill, enter 6");
		System.out.println("To get a loan, enter 7");
		System.out.println("To logout, enter -1");
	}
	
	public int getAuthenticationCode()
	{
		int code = (int) (Math.random() * 10000) + 1000;
		return code;
	}
	
	public Payee getPayee()
	{
		System.out.println("To pay the bank, enter 1");
		System.out.println("To pay the phone company, enter 2");
		System.out.println("To pay the water company, enter 3");
		System.out.println("To pay the electric company, enter 4");
		int payeeOpt;
		payeeOpt = sc.nextInt();
		Payee payee;
		switch (payeeOpt)
		{
		case 1:
			payee = Payee.BANK;
			break;
		case 2:
			payee = Payee.PHONE_COMPANY;
			break;
		case 3:
			payee = Payee.WATER_COMPANY;
			break;
		default:
			payee = Payee.ELECTRIC_COMPANY; 
		}
		return payee;
	}

	public static int getNumOfUsers() {

		return numOfUsers;
	}
	
	
}
