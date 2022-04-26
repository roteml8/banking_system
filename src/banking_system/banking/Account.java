package banking_system.banking;

import java.time.LocalDateTime;

public class Account {
	
	private int MAX_ACTIVITIES = 100;
	
	protected double balance;
	protected AccountProperties accProperties;
	protected ActivityData[] activities;
	protected int numActivities;
	protected double debt;
	protected LocalDateTime releaseTime;

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

	public AccountProperties getAccProperties() {
		return accProperties;
	}

	public int getNumActivities() {
		return numActivities;
	}
	
	public void showActivites()
	{
		for (int i=0; i<numActivities; i++)
		{
			ActivityData current = activities[i];
			if (current.timeStamp.isBefore(LocalDateTime.now()))
				System.out.println(current.info);
		}
		System.out.println("Current debt is: "+debt);
	}

	public double getBalance() {
		return balance;
	}

	public LocalDateTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(LocalDateTime releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	
	
}
