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
	
	// add a new account owner to users array
	private void addUser(AccountOwner owner)
	{
		users[numOfUsers++] = owner;
	}
	
	// set the bank manager
	private void setManager()
	{
		Credentials managerCred = new Credentials("rotemlevi", "rotem8");
		PhoneNumber managerPhone = new PhoneNumber(52, 5360337);
		LocalDate managerBday = LocalDate.of(1994, 8, 8);
		manager = new BankManager("Rotem", "Levi", managerPhone, managerBday, 50000, managerCred);
		users[numOfUsers++] = manager;
	}
	
	// set two account owners
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
	
	// run the application
	public void run()
	{
		System.out.println("Welcome to the AJBC Bank!");
		userMenu();
		
	}
	
	// login with username and password
	// if password is wrong, gives 3 more tries
	// after 3 more wrong tries, the account is blocked for 30 minutes
	// returns the logging account owner
	public AccountOwner login(String username, String password)
	{
		
		AccountOwner loggingOwner = getUserByUsername(username);
		
		if (loggingOwner == null)
		{
			System.out.println("No account owner with the given username.");
			return null;
		}
		Credentials ownerCredentials = loggingOwner.getCredentials();
		LocalDateTime currentRelease = loggingOwner.getAccount().getReleaseTime();
		if (currentRelease != null)
		{
			if (!checkRelease(currentRelease))
			{
				System.out.println("Your account has been blocked. please come back at "
					+currentRelease);
				return null;
			}
			loggingOwner.getAccount().setReleaseTime(null);
		}
		
		if (checkPassword(password, ownerCredentials.getPassword()))
		{
			System.out.println("Successfully logged in.");
			return loggingOwner;
		}
		if (giveUser3TriesForPassword(loggingOwner))
		{
			System.out.println("Successfully logged in.");
			return loggingOwner;
		}
		blockAccount(loggingOwner);
		return null;
	}
	
	// return if release time has passed
	public boolean checkRelease(LocalDateTime release)
	{
		if (release.isAfter(LocalDateTime.now()))
			return false;
		return true;
			
	}
	// block account of user 
	public void blockAccount(AccountOwner user)
	{
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime releaseTime = now.plusMinutes(30);
		user.getAccount().setReleaseTime(releaseTime);
		System.out.println("Your account has been blocked, come back at "+releaseTime);
	}
	
	// give user 3 tries to enter correct password
	// return true if succeeds, false otherwise
	public boolean giveUser3TriesForPassword(AccountOwner user)
	{
		Credentials userCredentials = user.getCredentials();
		int tries = 3;
		while (tries > 0)
		{
			
			System.out.printf("Wrong password! you have %d more tries\n", tries);
			System.out.println("Enter password");
			String currentTry = sc.next();
			if (checkPassword(currentTry, userCredentials.getPassword()))
				return true;
			else
				tries--;
		}
		return false;
	}
	
	// check equality of passwords
	public boolean checkPassword(String givenPassword, String actualPassword)
	{
		return givenPassword.equals(actualPassword);
	}
	
	// login with phone number
	// returns the loggin account owner, null if doesnt exist in the system
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
	
	// get account owner by phone number
	public static AccountOwner getOwnerByPhoneNum(PhoneNumber phoneNum)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getPhoneNum().equals(phoneNum))
				return users[i];
		}
		return null;
	}
	
	// logout of the system
	public void logout()
	{
		System.out.println("Successfully logged out.");
		currUser = null;
	}
	
	// returns account owner with the given username
	// null if no such username
	public AccountOwner getUserByUsername(String username)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getCredentials().getUsername().equals(username))
				return users[i];
		}
		return null;
	}
	
	public PhoneNumber getPhoneFromInput()
	{
		System.out.println("Enter phone number area code");
		int areaCode = sc.nextInt();
		System.out.println("Enter phone number");
		int phoneNum = sc.nextInt();
		PhoneNumber newPhone = new PhoneNumber(areaCode, phoneNum);
		return newPhone;
	}
	
	public LocalDate getBirthdateFromInput()
	{
		System.out.println("Enter year of birth");
		int year = sc.nextInt();
		System.out.println("Enter month of birth (1-12)");
		int month = sc.nextInt();
		System.out.println("Enter day of birth (1-31)");
		int day = sc.nextInt();
		sc.nextLine();
		LocalDate birthDate = LocalDate.of(year, month, day);
		return birthDate;
	}
	
	public String getNameFromUser(String kind)
	{
		System.out.println("Enter "+kind+" name");
		String input = sc.next();
		return input;
	}
	
	public String getUsernameFromInput()
	{
		System.out.println("Enter username: letters and digits only");
		String username = sc.next();
		return username;
	}
	
	public String getPasswordFromInput()
	{
		System.out.println("Please enter password: 4-8 characters, must contain a digit and a letter");
		String password = sc.next();
		return password;
	}
	
	public double getMonthlyIncomeFromInput()
	{
		System.out.println("Please enter your monthly income");
		double income = sc.nextDouble();
		return income;
	}
	// open a new account 
	// add new user to users array
	// add new user to the manager's users to approve array
	public void openAccount()
	{
		PhoneNumber newPhone = getPhoneFromInput();
		if (getOwnerByPhoneNum(newPhone) != null)
		{
			System.out.println("There is already an account with this phone number. please login or register with a different number");
			return;
		}
		sc.nextLine();

		String name = getNameFromUser("first");
		String lastName = getNameFromUser("last");
		LocalDate birthDate = getBirthdateFromInput();
		String username = getUsernameFromInput();
		if (getUserByUsername(username) != null)
		{
			System.out.println("Username already exists in the system. please login");
			return;
		}
		while (!Credentials.isUsernameLegal(username))
			username = getUsernameFromInput();
		String password = getPasswordFromInput();
		while (!Credentials.isPasswordLegal(password))
			password = getPasswordFromInput();
		Credentials newCred = new Credentials(username, password);
		double income = getMonthlyIncomeFromInput();
		AccountOwner newOwner = new AccountOwner(name, lastName, newPhone, birthDate, income, newCred);
		addUser(newOwner);
		manager.addUserToApprove(newOwner);
		System.out.println("Application completed. waiting for managar approval and setting.");
		
	}
	
	
	public void printUserOptions()
	{
		System.out.println("\nTo open account, enter 1");
		System.out.println("To login with username and password, enter 2");
		System.out.println("To login with phone number, enter 3");
		System.out.println("To exit the system, enter -1");
	}
	
	// main menu for login, open account and exit system
	public void userMenu()
	{
		printUserOptions();
		int choice = sc.nextInt();
		sc.nextLine();
		String username, password;
		while (choice != -1)
		{
			switch (choice)
			{
			case 1:
				openAccount();
				break;
			case 2:
				username = getUsernameFromInput();
				password = getPasswordFromInput();
				currUser = login(username, password);
				if (currUser != null)
					if (currUser instanceof BankManager)
						managerMenu();
					else
						accountOwnerMenu();
				break;
			case 3:
				PhoneNumber currPhone = getPhoneFromInput();
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
	
	// menu for account owners and logout
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
	
	// menu for bank manager for set and approve accounts and access to account owners menu
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
	
	// get a 4 digit authentication code
	public int getAuthenticationCode()
	{
		int code = (int) (Math.random() * 9000) + 1000;
		return code;
	}
	
	// get payee for bill payment
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
	
	public static void printAllUsers()
	{
		for (int i=0; i< numOfUsers; i++)
			System.out.println(users[i]);
	}
	
}
