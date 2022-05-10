package banking_system.app;

import java.time.LocalDate;

import banking_system.banking.Account;
import banking_system.banking.AccountProperties;
import banking_system.users.AccountOwner;
import banking_system.users.Credentials;
import banking_system.users.PhoneNumber;

/*
 * a class that represents the bank manager 
 */
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


	/**
	 * approves users application and creates their accounts
	 * sets account properties by monthly income
	 * set number of users to approve to 0
	 */
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
			AccountProperties currentProp = AccountProperties.getAccountType(currentOwner.getMonthlyIncome());
			Account newAccount = new Account(currentProp);
			usersToApprove[i].setAccount(newAccount);
			System.out.println("Set the account ID: "+newAccount.getID()+" of "+currentOwner.getFullName()+
					" to: "+currentProp.toString());
		}
		
		numUsersToApprove = 0;
	}
	
	/** 
	 * add a new user to the array of users to approve
	 * @param accountOwner the new user to add
	 */
	public void addUserToApprove(AccountOwner accountOwner)
	{
		usersToApprove[numUsersToApprove++] = accountOwner;
		
	}
	
	@Override
	/**
	 * produce activity report for manager account (bank account)
	 * @param start start date for the report
	 */
	public void produceReport(LocalDate start)
	{
		System.out.println("Presenting report for bank account: ");
		System.out.println("------------------------------");
		super.produceReport(start);
	}
	


}
