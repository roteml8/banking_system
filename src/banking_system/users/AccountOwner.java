package banking_system.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountOwner extends Person {
	
	private final static int MAX_MONTHLY_PAYMENTS = 60;
	private final static int MAX_BILL_PAYMENT = 5000;
	private final static int MAX_TRANSFER = 2000;

		
	protected Account account;
	protected double monthlyIncome;
	protected Credentials credentials;
	
	public AccountOwner(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate,
			double monthlyIncome, Credentials credentials) {
		super(firstName, lastName, phoneNum, birthDate);
		setMonthlyIncome(monthlyIncome);
		setCredentials(credentials);
		this.account = null;
	}




	public void setMonthlyIncome(double monthlyIncome) {
		if (monthlyIncome < 0)
			monthlyIncome = 0;
		this.monthlyIncome = monthlyIncome;
	}




	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}




	public void checkBalance()
	{
		System.out.println("Current balance is: "+account.balance);
	}
	
	public void produceReport(LocalDate start)
	{
		for (int i=0; i<account.numActivities; i++)
		{
			ActivityData current = account.activities[i];
			if (current.timeStamp.isBefore(LocalDateTime.now()))
				System.out.println(current.info);
		}
		checkBalance();
		//TODO
		System.out.println("Current debt is: "+account.debt);
		// current debt
		
	}
	
	public void deposit(double amount)
	{
		//TODO
		// check deposit is possible...
		
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Deposit of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.DEPOSIT, amount, now, info);
		account.addActivity(newActivity);
		account.changeBalance(amount);
	}
	
	public void withdrawl(double amount)
	{
		//TODO
		// check withdrawal is possible...
		if (amount > account.accProperties.dailyMax)
		{
			System.out.println("Operation is impossible due to amount exceeding limit");
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Withdrawl of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.WITHDRAWAL, -amount, now, info);
		account.addActivity(newActivity);
		account.changeBalance(-amount);
	}
	
	public void transferFunds(double amount, Account receiver)
	{
		//TODO
		// check if possible...
		if (amount > MAX_TRANSFER)
		{
			System.out.println("Operation is impossible due to amount exceeding limit");
			return;
		}
		
		
		// register withdrawal from sending account
		// register deposit to receiving account
		
	}
	
	public void payBill(double amount, Payee payee)
	{
		//TODO
		// check if possible...
		if (amount > MAX_BILL_PAYMENT)
		{
			System.out.println("Operation is impossible due to amount exceeding limit");
			return;
		}
		account.changeBalance(-amount);
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Bill payment of %f NIS to %s", amount, payee.toString());
		ActivityData newActivity = new ActivityData(ActivityName.PAY_BIll, -amount, now, info);
		account.addActivity(newActivity);
		if (payee == Payee.BANK)
		{
			// deposit to bank
			// register deposit to bank 
			account.changeDebt(-amount);
		}
		
	}
	
	public void getLoan(double amount, int numOfMonths)
	{
		//TODO
		// check if possible...
		if (numOfMonths > MAX_MONTHLY_PAYMENTS || amount > account.accProperties.maxLoan)
		{
			System.out.println("Operation is impossible due to illegal parameters!");
			return;
		}
		double monthlyReturn = amount/numOfMonths;
		System.out.println("Amount of monthly return is: "+monthlyReturn);
		// bank.withdrawal()
		
		account.changeBalance(amount);
		account.changeDebt(amount);
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Loan of %f NIS from the bank, monthly payment: %f", amount, monthlyReturn);
		// add interest to info
		ActivityData newActivity = new ActivityData(ActivityName.GET_LOAN, amount, now, info);
		account.addActivity(newActivity);
	}


}
