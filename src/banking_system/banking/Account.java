package banking_system.banking;

import java.time.LocalDateTime;

import banking_system.app.AppManager;

public class Account {
	
	private int MAX_ACTIVITIES = 100;
	private static int COUNTER = 1;
	
	protected double balance;
	protected AccountProperties accProperties;
	protected ActivityData[] activities;
	protected int numActivities;
	protected double debt;
	protected LocalDateTime releaseTime;
	protected final int ID;

	public Account(AccountProperties accProperties)
	{
		this.balance = 0;
		this.numActivities = 0;
		this.activities = new ActivityData[MAX_ACTIVITIES];
		this.accProperties = accProperties;
		this.debt = 0;
		this.ID = COUNTER++;

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
		return "Account ID: "+ID+", Account Type:" + accProperties;
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
				System.out.println(current);
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
	
	// pay fee for bank
	// register deposit to bank
	// register fee collection to owner
	public void payFee()
	{
		double fee = accProperties.feeLow;
		changeBalance(-fee);
		LocalDateTime now = LocalDateTime.now();
		AppManager.manager.getAccount().changeBalance(fee); 
		ActivityData feeBankData = new ActivityData(ActivityName.DEPOSIT, fee, now ,"Fee collection from account: "+ID);
		AppManager.manager.getAccount().addActivity(feeBankData);
		ActivityData ownerData = new ActivityData(ActivityName.FEE_COLLECTION, -fee, now, "Fee collection to bank");
		addActivity(ownerData);
	}
	
}
