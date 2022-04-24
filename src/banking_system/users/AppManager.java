package banking_system.users;

import java.time.LocalDateTime;

public class AppManager {
	
	protected static AccountOwner currUser;
	protected static AccountOwner[] users;
	
	public void login(String username, String password)
	{
		int tries = 3;
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
			return;
		}
		while (tries > 0)
		{
			if (ownerCredentials.password.equals(password))
			{
				System.out.println("Successfully logged in.");
				currUser = loggingOwner;
				return;
			}
			else
			{
				tries--;
				System.out.printf("Wrong password! you have %d more tries\n", tries);
			}
		}
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Your account has been blocked, come back at "+now.plusMinutes(30));
	}
	
	public void login(int areaCode, int number)
	{
		PhoneNumber givenPhoneNum = new PhoneNumber(areaCode, number);
		AccountOwner owner = getOwnerByPhoneNum(givenPhoneNum);
		if (owner == null)
		{
			System.out.println("No user with the given phone number.");
			return;
		}
		System.out.println("Successfully logged in.");
		currUser = owner;
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

}
