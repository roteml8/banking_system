package banking_system.users;

import java.time.LocalDate;

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
		//this.account.changeBalance(50000);
	}



	public void setAndApproveAcc()
	{
		int accountType;
		AccountProperties[] types = AccountProperties.values();
		for (int i=0; i<numUsersToApprove; i++)
		{
			accountType = (int) Math.random() * 4;
			usersToApprove[i].account = new Account(types[accountType]);
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
		super.produceReport(start);
	}
	

	
	

}
