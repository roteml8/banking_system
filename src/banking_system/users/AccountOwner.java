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
			double monthlyIncome) {
		super(firstName, lastName, phoneNum, birthDate);
		this.monthlyIncome = monthlyIncome;
	}
	
	
	public void checkBalance()
	{
		System.out.println("Current balance is: "+account.balance);
	}
	
	public void produceReport(LocalDate start)
	{
		//TODO
		
	}
	
	public void deposit(double amount)
	{
		//TODO
		// check deposit is possible...
		
		account.balance += amount;
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Deposit of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.DEPOSIT, amount, now, info);
		account.addActivity(newActivity);
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
		account.balance -= amount;
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Withdrawl of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.WITHDRAWAL, -amount, now, info);
		account.addActivity(newActivity);
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
		account.balance -= amount;
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Bill payment of %f NIS to %s", amount, payee.toString());
		ActivityData newActivity = new ActivityData(ActivityName.PAY_BIll, -amount, now, info);
		account.addActivity(newActivity);
		if (payee == Payee.BANK)
		{
			// deposit to bank
			// register deposit to bank 
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
		double montlyReturn = amount/numOfMonths;
		System.out.println("Amount of monthly return is: "+montlyReturn);
		deposit(amount);
		// bank.withdrawal()
		
		account.balance += amount;
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Loan of %f NIS from the bank", amount);
		ActivityData newActivity = new ActivityData(ActivityName.GET_LOAN, -amount, now, info);
		account.addActivity(newActivity);
	}

}
