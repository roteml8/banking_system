package banking_system.users;

import java.time.LocalDate;

public class BankManager extends AccountOwner {
	
	private final int MAX_USERS = 100;
	
	protected AccountOwner[] usersToApprove;
	protected int numUsersToApprove;

	public BankManager(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate,
			double monthlyIncome) {
		super(firstName, lastName, phoneNum, birthDate, monthlyIncome);
		this.usersToApprove = new AccountOwner[MAX_USERS];
		this.numUsersToApprove = 0;
	}
	
	

}
