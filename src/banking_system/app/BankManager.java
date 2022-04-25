package banking_system.app;

import java.time.LocalDate;

import banking_system.banking.Account;
import banking_system.banking.AccountProperties;
import banking_system.users.AccountOwner;
import banking_system.users.Credentials;
import banking_system.users.PhoneNumber;

public class BankManager extends AccountOwner {
	
	private final int MAX_USERS = 100;
	
	protected AccountOwner[] usersToApprove;
	protected int numUsersToApprove;

	public BankManager(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate,
			double monthlyIncome, Credentials credentials) {
		super(firstName, lastName, phoneNum, birthDate, monthlyIncome, credentials);
		this.numUsersToApprove = 0;
		this.usersToApprove = new AccountOwner[MAX_USERS];
		this.account = new Account(AccountProperties.TITANIUM);
		this.account.changeBalance(500000);
	}



	public void setAndApproveAcc()
	{
		if (numUsersToApprove == 0)
		{
			System.out.println("No users waiting for approval.");
			return;
		}
		for (int i=0; i<numUsersToApprove; i++)
		{
			AccountOwner currentOwner = usersToApprove[i];
			AccountProperties currentProp = getAccountType(currentOwner.getMonthlyIncome());
			usersToApprove[i].setAccount(new Account(currentProp));
			System.out.println("Set the account of "+currentOwner.getFullName()+
					" to: "+currentProp.toString());
		}
		
		numUsersToApprove = 0;
	}
	
	public void addUserToApprove(AccountOwner accountOwner)
	{
		usersToApprove[numUsersToApprove++] = accountOwner;
		
	}
	
	@Override
	public void produceReport(LocalDate start)
	{
		System.out.println("Presenting report for bank account: ");
		super.produceReport(start);
	}
	
	public AccountProperties getAccountType(double income)
	{
		if (income > 50000)
			return AccountProperties.TITANIUM;
		else if (income > 20000)
			return AccountProperties.GOLD;
		else if (income > 10000)
			return AccountProperties.SILVER;
		else
			return AccountProperties.BRONZE;
	}
	
	

}
