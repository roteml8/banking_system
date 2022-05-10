package banking_system.banking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import banking_system.app.AppManager;

/**
 * a class that represents a bank account and its data
 * @author Rotem
 *
 */
public class Account {
	
	private static int COUNTER = 1;
	
	protected double balance;
	protected AccountProperties accProperties;
	protected ArrayList<ActivityData> activities;
	protected double debt;
	protected LocalDateTime releaseTime;
	protected final int ID;

	public Account(AccountProperties accProperties)
	{
		this.balance = 0;
		this.activities = new ArrayList<>();
		this.accProperties = accProperties;
		this.debt = 0;
		this.ID = COUNTER++;

	}
	
	/**
	 * update account balance
	 * @param amount the change to apply to the balance
	 */
	public void changeBalance(double amount)
	{
		balance += amount;
	}
	
	/**
	 * update account debt 
	 * @param amount the change to apply to the debt
	 */
	public void changeDebt(double amount)
	{
		debt += amount;
	}
	
	/**
	 * add activity data to account array of activities
	 * @param activityData the activity data to add
	 */
	public void addActivity(ActivityData activityData)
	{
		activities.add(activityData);
		
	}

	@Override
	public String toString() {
		return "Account ID: "+ID+", Account Type:" + accProperties;
	}

	public AccountProperties getAccProperties() {
		return accProperties;
	}

	/**
	 * print all account activities starting from the given start date 
	 * @param start the given start date
	 */
	public void showActivites(LocalDate start)
	{
		for (ActivityData activity: activities)
		{
			LocalDate timeStamp = activity.timeStamp.toLocalDate();
			if (timeStamp.isAfter(start) || timeStamp.isEqual(start))
				System.out.println(activity);
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
	
	/**
	 * pay fee for the bank
	 * register deposit to bank
	 * register fee collection to owner
	 */
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

	public int getID() {
		return ID;
	}
	
	
}
