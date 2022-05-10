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

/**
 * the class that manages the application
 * @author Rotem
 *
 */

//TODO: move adding owner for manager approval to account owner
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
	
	/**
	 * add a new account owner to users array
	 * @param owner the account owner to add
	 */
	private void addUser(AccountOwner owner)
	{
		users[numOfUsers++] = owner;
	}
	
	/**
	 * set the default bank manager
	 */
	private void setManager()
	{
		Credentials managerCred = new Credentials("rotemlevi", "rotem8");
		PhoneNumber managerPhone = new PhoneNumber(52, 5360337);
		LocalDate managerBday = LocalDate.of(1994, 8, 8);
		manager = new BankManager("Rotem", "Levi", managerPhone, managerBday, 50000, managerCred);
		users[numOfUsers++] = manager;
	}
	
	/**
	 * set two default account owners
	 */
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
	
	/**
	 * run the application and present main menu
	 */
	public void run()
	{
		System.out.println("Welcome to the AJBC Bank!");
		userMenu();
		
	}
	
	public boolean isUserApproved(AccountOwner owner)
	{
		if (owner.getAccount() == null)
		{
			System.out.println("User hasn't been approved by manager yet.");
			return false;

		}
		return true;
	}
	
	
	/**
	 *  login with username and password
	 *	if password is wrong, gives 3 more tries
	 *	after 3 more wrong tries, the account is blocked for 30 minutes
	 *	returns the logging account owner
	 * @param username the account owner username
	 * @param password the account owner password
	 * @return the logged in account owner 
	 */
	public AccountOwner login(String username, String password)
	{
		
		AccountOwner loggingOwner = getUserByUsername(username);
		
		if (loggingOwner == null)
		{
			System.out.println("No account owner with the given username.");
			return null;
		}
		if (!isUserApproved(loggingOwner))
			return null;
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
	
	/**
	 * check if release time has passed, false otherwise
	 * @param release the account release time 
	 * @return true if release time has passed, false otherwise
	 */
	public boolean checkRelease(LocalDateTime release)
	{
		if (release.isAfter(LocalDateTime.now()))
			return false;
		return true;
			
	}
	
	/**
	 * block account of user
	 * @param user the account owner to block their account
	 */
	public void blockAccount(AccountOwner user)
	{
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime releaseTime = now.plusMinutes(30);
		user.getAccount().setReleaseTime(releaseTime);
		System.out.println("Your account has been blocked, come back at "+releaseTime);
	}
	

	/**
	 * gives user 3 tries to enter correct password
	 * @param user the account owner that is trying to log in 
	 * @return true if user succeeds, false otherwise
	 */
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
	
	/**
	 * check equality of given password to the actual password
	 * @param givenPassword the password that the users enter
	 * @param actualPassword the user's actual password
	 * @return true if passwords are equal, false otherwise
	 */
	public boolean checkPassword(String givenPassword, String actualPassword)
	{
		return givenPassword.equals(actualPassword);
	}
	

	/**
	 * login with phone number
	 * @param phoneNum given phone number
	 * @return the logging account owner, null if doesnt exist in the system
	 */
	public AccountOwner login(PhoneNumber phoneNum)
	{
		AccountOwner owner = getOwnerByPhoneNum(phoneNum);
		if (owner == null)
		{
			System.out.println("No user with the given phone number.");
			return null;
		}
		if (!isUserApproved(owner))
			return null;
		LocalDateTime currentRelease = owner.getAccount().getReleaseTime();
		if (currentRelease != null)
		{
			if (!checkRelease(currentRelease))
			{
				System.out.println("Your account has been blocked. please come back at "
					+currentRelease);
				return null;
			}
			owner.getAccount().setReleaseTime(null);
		}
		System.out.println("Successfully logged in.");
		return owner;
	}
	
	
	/**
	 *  get account owner by phone number
	 * @param phoneNum the given phone number
	 * @return the account owner with the given phone number, 
	 * null if there is no user with this number
	 */
	public static AccountOwner getOwnerByPhoneNum(PhoneNumber phoneNum)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getPhoneNum().equals(phoneNum))
				return users[i];
		}
		return null;
	}
	
	/**
	 * logout of the system
	 * set current user to null
	 */
	public void logout()
	{
		System.out.println("Successfully logged out.");
		currUser = null;
	}
	
	
	/** 
	 * get user by username
	 * @param username
	 * @return the account owner with the given username,
	 * null if no user with the given username
	 */
	public AccountOwner getUserByUsername(String username)
	{
		for (int i=0; i<numOfUsers; i++)
		{
			if (users[i].getCredentials().getUsername().equals(username))
				return users[i];
		}
		return null;
	}
	
	/**
	 * get phone number from user by area code and number
	 * @return the given phone number
	 */
	public PhoneNumber getPhoneFromInput()
	{
		System.out.println("Enter phone number area code");
		int areaCode = sc.nextInt();
		System.out.println("Enter phone number");
		int phoneNum = sc.nextInt();
		PhoneNumber newPhone = new PhoneNumber(areaCode, phoneNum);
		return newPhone;
	}
	
	/**
	 * get date from user by year, month and day of month
	 * @return the given date
	 */
	public LocalDate getDateFromInput()
	{
		System.out.println("Enter year");
		int year = sc.nextInt();
		System.out.println("Enter month (1-12)");
		int month = sc.nextInt();
		System.out.println("Enter day of month (1-31)");
		int day = sc.nextInt();
		sc.nextLine();
		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}
	
	/** 
	 * get name from user (first name and last name)
	 * @param kind the requested name (first/last)
	 * @return the given name
	 */
	public String getNameFromUser(String kind)
	{
		System.out.println("Enter "+kind+" name");
		String input = sc.next();
		return input;
	}
	
	/**
	 * get username from user (for login and creating account)
	 * @return the given username
	 */
	public String getUsernameFromInput()
	{
		System.out.println("Enter username: letters and digits only");
		String username = sc.next();
		return username;
	}
	
	/**
	 * get password from user (for login and creating account)
	 * @return the given password
	 */
	public String getPasswordFromInput()
	{
		System.out.println("Please enter password: 4-8 characters, must contain a digit and a letter");
		String password = sc.next();
		return password;
	}
	
	/**
	 * get monthly income from the user
	 * @return the monthly income
	 */
	public double getMonthlyIncomeFromInput()
	{
		System.out.println("Please enter your monthly income");
		double income = sc.nextDouble();
		return income;
	}
	
	/**
	 * open a new account
	 * add new user to users array
	 * add new user to the manager's users to approve array
	 */
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
		System.out.println("Enter birthday");
		LocalDate birthDate = getDateFromInput();
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
	
	
	/**
	 * print the main menu
	 */
	public void printUserOptions()
	{
		System.out.println("\nTo open account, enter 1");
		System.out.println("To login with username and password, enter 2");
		System.out.println("To login with phone number, enter 3");
		System.out.println("To exit the system, enter -1");
	}
	

	/**
	 *  main menu for login, opening account and exit system
	 */
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
	
	
	/**
	 * menu for account owners and logout
	 */
	public void accountOwnerMenu()
	{
		printOwnerOptions();
		int choice = sc.nextInt();
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
				System.out.println("Enter start date for report:");
				LocalDate start = getDateFromInput();
				currUser.produceReport(start);
				break;
			case 3:
				System.out.println("Your authentication code is "+getAuthenticationCode());
				amount = getAmountFromUser("deposit");
				currUser.deposit(amount);
				break;
			case 4:
				amount = getAmountFromUser("withdraw");
				currUser.withdrawl(amount);
				break;
			case 5:
				System.out.println("Enter phone number of receiver:");
				phoneNum = getPhoneFromInput();
				AccountOwner receiver = getOwnerByPhoneNum(phoneNum);
				if (receiver == null)
				{
					System.out.println("No user with given phone number, operation terminates.");
					break;
				}
				amount = getAmountFromUser("transfer");
				currUser.transferFunds(amount, receiver);
				break;
			case 6:
				Payee payee = getPayee();
				amount = getAmountFromUser("pay bill");
				currUser.payBill(amount, payee);
				break;
			case 7: 
				amount = getAmountFromUser("loan");
				int months =  getNumberOfMonthsFromInput();
				currUser.getLoan(amount, months);
				break;
			default:
				break;
			}
			printOwnerOptions();
			choice = sc.nextInt();
		}
		
	}
	
	/**
	 * get number of monthly payments from user
	 * @return the number of months
	 */
	public int getNumberOfMonthsFromInput()
	{
		System.out.println("Enter number of monthly payments");
		int months = sc.nextInt();
		return months;
	}
	
	/**
	 * get amount of money from the user
	 * @param purpose a string that describes the purpose of the money 
	 * @return the given amount of money
	 */
	public double getAmountFromUser(String purpose)
	{
		System.out.println("Enter amount to "+purpose);
		double amount = sc.nextDouble();
		return amount;
	}
	
	/**
	 * menu for bank manager for set and approve accounts and access to account owners menu
	 */
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
	
	/**
	 * print menu for manager
	 */
	public void printManagerOptions()
	{
		System.out.println("To approve and set accounts, enter 1");
		System.out.println("To owner menu, enter 2");
		System.out.println("To main menu, enter -1");
	}
	
	/**
	 * print menu for account owners
	 */
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
	
	/**
	 * generate a 4 digit authentication code for deposit
	 * @return a 4 digit authentication code
	 */
	public int getAuthenticationCode()
	{
		int code = (int) (Math.random() * 9000) + 1000;
		return code;
	}
	
	
	/**
	 * get payee for bill payment according to user input
	 * @return the chosen payee
	 */
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
	
	/**
	 * prints to the screen all users in the system
	 */
	public static void printAllUsers()
	{
		for (int i=0; i< numOfUsers; i++)
			System.out.println(users[i]);
	}
	
}
