package banking_system.users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AppManager {
	
	protected static AccountOwner currUser;
	protected static AccountOwner[] users;
	protected static BankManager manager;
	
	protected static Scanner sc = new Scanner(System.in);
	
	public AccountOwner login(String username, String password)
	{
		
		AccountOwner loggingOwner = null;
		Credentials ownerCredentials = null;
		for (int i=0; i<users.length && loggingOwner == null; i++)
		{
			Credentials currentCredentials = users[i].credentials;
			if (currentCredentials.username.equals(username))
			{
				loggingOwner = users[i];
				ownerCredentials = users[i].credentials;
			}
		}
		if (loggingOwner == null)
		{
			System.out.println("No account owner with the given username.");
			return null;
		}
		if (ownerCredentials.password.equals(password))
		{
			System.out.println("Successfully logged in.");
			return loggingOwner;
		}
		int tries = 3;
		while (tries > 0)
		{
			
			System.out.printf("Wrong password! you have %d more tries\n", tries);
			System.out.println("Enter password");
			String currentTry = sc.nextLine();
			if (ownerCredentials.password.equals(currentTry))
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
		for (AccountOwner user: users)
		{
			if (user.phoneNum.equals(phoneNum))
				return user;
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
		for (AccountOwner user: users)
		{
			if (user.credentials.username.equals(username))
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
		System.out.println("Enter your first name");
		String name = sc.nextLine();
		System.out.println("Enter your last name");
		String lastName = sc.nextLine();
		System.out.println("Enter year of birth");
		int year = sc.nextInt();
		System.out.println("Enter month of birth (1-12)");
		int month = sc.nextInt();
		System.out.println("Enter day of birth (1-31)");
		int day = sc.nextInt();
		LocalDate birthDate = LocalDate.of(year, month, day);
		System.out.println("Enter username: letters and digits only");
		String username = sc.nextLine();
		if (doesUsernameExist(username))
		{
			System.out.println("Username already exists in the system. please login");
			return;
		}
		while (!Credentials.isUsernameLegal(username))
		{
			System.out.println("Invalid username, please enter a username according to the instructions");
			username = sc.nextLine();
		}
		System.out.println("Please enter password: 4-8 characters, must contain a digit and a letter");
		String password = sc.nextLine();
		while (!Credentials.isPasswordLegal(password))
		{
			System.out.println("Invalid password, please enter a password according to the instructions");
			password = sc.nextLine();
		}
		Credentials newCred = new Credentials(username, password);
		System.out.println("Please enter your monthly income");
		double income = sc.nextDouble();
		AccountOwner newOwner = new AccountOwner(name, lastName, newPhone, birthDate, income, newCred);
		//TODO add new owner to DB
		manager.addUserToApprove(newOwner);
		System.out.println("Application completed. waiting for managar approval and setting.");
		
	}
	
	public void run()
	{
		//TODO
		int choice;
		System.out.println("Welcome to the AJBC Bank!");
		printUserMenu();
		
	}
	
	public void printUserOptions()
	{
		System.out.println("To open account, enter 1");
		System.out.println("To login with username and password, enter 2");
		System.out.println("To login with phone number, enter 3");
		System.out.println("To exit the system, enter -1");
	}
	
	public void userMenu()
	{
		printUserOptions();
		int choice = sc.nextInt();
		int areaCode, number;
		String username, password;
		AccountOwner loggedOwner;
		while (choice != -1)
		{
			switch (choice)
			{
			case 1:
				openAccount();
				break;
			case 2:
				System.out.println("Enter username");
				username = sc.nextLine();
				System.out.println("Enter password");
				password = sc.nextLine();
				loggedOwner = login(username, password);
				if (loggedOwner != null)
					accountOwnerMenu();
				break;
			case 3:
				System.out.println("Enter phone area code");
				areaCode = sc.nextInt();
				System.out.println("Enter phone number");
				number = sc.nextInt();
				PhoneNumber currPhone = new PhoneNumber(areaCode, number);
				loggedOwner = login(currPhone);
				if (loggedOwner != null)
					accountOwnerMenu();
				break;
			default:
				break;
			}
			printUserOptions();
			choice = sc.nextInt();
		}
		System.out.println("Goodbye!");
	}
	
	public void accountOwnerMenu()
	{
		printOwnerOptions();
		int choice = sc.nextInt();
		while (choice != -1)
		{
			switch (choice)
			{
			
			}
		}
		
	}
	
	public void printOwnerOptions()
	{
		System.out.println("To check your balance, enter 1");
		System.out.println("To produce an activity report, enter 2");
		System.out.println("To made a deposit, enter 3");
		System.out.println("To make a withdrawal, enter 4");
		System.out.println("To transfer funds, enter 5");
		System.out.println("To pay a bill, enter 6");
		System.out.println("To get a loan, enter 7");
		System.out.println("To logout, enter -1");
	}
}
