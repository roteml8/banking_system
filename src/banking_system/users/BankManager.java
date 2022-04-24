package banking_system.users;

import java.time.LocalDate;

public class BankManager extends AccountOwner {
	
	private final int MAX_USERS = 100;
	
	protected AccountOwner[] usersToApprove;
	protected int numUsersToApprove;


	public BankManager(String firstName, String lastName, int areaCode, int number, int day, int month, int year,
			Account account, double monthlyIncome, Credentials credentials) {
		super(firstName, lastName, areaCode, number, day, month, year, account, monthlyIncome, credentials);
		this.numUsersToApprove = 0;
		this.usersToApprove = new AccountOwner[MAX_USERS];

	}

	public void setAndApproveAcc()
	{
		//TODO
		for (int i=0; i<numUsersToApprove; i++)
		{
			
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
		//TODO
	}
	
	

}
