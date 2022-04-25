package banking_system.users;

public class Account {
	
	private int MAX_ACTIVITIES = 100;
	
	protected double balance;
	protected AccountProperties accProperties;
	protected ActivityData[] activities;
	protected int numActivities;
	protected double debt;

	public Account(AccountProperties accProperties)
	{
		this.balance = 0;
		this.numActivities = 0;
		this.activities = new ActivityData[MAX_ACTIVITIES];
		this.accProperties = accProperties;
		this.debt = 0;
	}
	
	public void changeBalance(double amount)
	{
		balance += amount;
	}
	
	public void changeDebt(double amount)
	{
		debt += amount;
	}
	
	public void addActivity(ActivityData activityData)
	{
		if (numActivities < MAX_ACTIVITIES)
			activities[numActivities++] = activityData;
		
	}

	@Override
	public String toString() {
		return "Account [accProperties=" + accProperties + "]";
	}
	
	
}
