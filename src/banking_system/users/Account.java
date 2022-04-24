package banking_system.users;

public class Account {
	
	private int MAX_ACTIVITIES = 100;
	
	protected double balance;
	protected AccountProperties accProperties;
	protected ActivityData[] activities;
	protected int numActivities;

	public Account(AccountProperties accProperties)
	{
		this.balance = 0;
		this.numActivities = 0;
		this.activities = new ActivityData[MAX_ACTIVITIES];
		this.accProperties = accProperties;
	}
	
	
}
