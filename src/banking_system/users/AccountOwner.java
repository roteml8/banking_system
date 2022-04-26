package banking_system.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

import banking_system.app.AppManager;
import banking_system.banking.Account;
import banking_system.banking.ActivityData;
import banking_system.banking.ActivityName;
import banking_system.banking.Payee;

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
		System.out.println("Current balance is: "+account.getBalance());
	}
	
	public void produceReport(LocalDate start)
	{
		System.out.println(this);
		account.showActivites();
		checkBalance();
		
	}
	
	public void deposit(double amount)
	{
		// check deposit is possible...
		
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Deposit of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.DEPOSIT, amount, now, info);
		account.addActivity(newActivity);
		account.changeBalance(amount);
		account.payFee();
		System.out.println("Successfully completed deposit.\n");
	}
	
	public void withdrawl(double amount)
	{
		// check withdrawal is possible...
		if (amount > account.getAccProperties().dailyMax)
		{
			System.out.println("Operation is impossible due to amount exceeding limit");
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		String info = String.format("Withdrawl of %f NIS", amount);
		ActivityData newActivity = new ActivityData(ActivityName.WITHDRAWAL, -amount, now, info);
		account.addActivity(newActivity);
		account.changeBalance(-amount);
		account.payFee();
		System.out.println("Successfully completed withdrawal.\n");

	}
	
	public void transferFunds(double amount, AccountOwner receiver)
	{
		// check if possible...
		if (amount > MAX_TRANSFER)
		{
			System.out.println("Operation is impossible due to amount exceeding limit");
			return;
		}
		receiver.account.changeBalance(amount);
		account.changeBalance(-amount);
		LocalDateTime now = LocalDateTime.now();
		String senderInfo = String.format("Transfer %f NIS to %s", amount, receiver.getFullName());
		String receiverInfo = String.format("Recevied %f NIS from %s", amount, getFullName());
		ActivityData receiverData = new ActivityData(ActivityName.DEPOSIT, amount, now, receiverInfo);
		ActivityData senderData = new ActivityData(ActivityName.TRANSFER, -amount, now, senderInfo);
		account.addActivity(senderData);
		receiver.account.addActivity(receiverData);
		account.payFee();
		System.out.println("Successfully transfered funds.\n");
	}
	
	public void payBill(double amount, Payee payee)
	{
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
		account.payFee();
		if (payee == Payee.BANK)
		{ 
			account.changeDebt(-amount);
			
			AppManager.manager.account.changeBalance(amount);
			String bankInfo = String.format("Deposit of %f NIS returned from %s", amount, getFullName());
			ActivityData bankActivity = new ActivityData(ActivityName.DEPOSIT, amount, now, bankInfo);
			AppManager.manager.account.addActivity(bankActivity);
		}
		System.out.println("Successfully completed bill payment.\n");

		
	}
	
	public void getLoan(double amount, int numOfMonths)
	{
		// check if possible...
		if (numOfMonths > MAX_MONTHLY_PAYMENTS || amount > account.getAccProperties().maxLoan)
		{
			System.out.println("Operation is impossible due to illegal parameters!");
			return;
		}
		double interest = account.getAccProperties().interestLow;
		double totalAmount = amount + (interest/100)*amount;
		double monthlyReturn = totalAmount/numOfMonths;
		System.out.println("Amount of monthly return is: "+monthlyReturn);
		
		LocalDateTime now = LocalDateTime.now();
		String bankInfo = String.format("Withdrawal of %f NIS for loan to %s", amount, getFullName());
		ActivityData bankActivity = new ActivityData(ActivityName.WITHDRAWAL, -amount, now, bankInfo);
		AppManager.manager.account.changeBalance(-amount);
		
		AppManager.manager.account.addActivity(bankActivity);
		account.changeBalance(amount);
		account.changeDebt(totalAmount);
		String info = String.format("Loan of %f NIS from the bank, monthly payment: %f\n", amount, monthlyReturn);
		info += String.format("Interest range: %f-%f\n", account.getAccProperties().interestLow, account.getAccProperties().interestHigh);
		ActivityData newActivity = new ActivityData(ActivityName.GET_LOAN, amount, now, info);
		account.addActivity(newActivity);
		account.payFee();
		System.out.println("Successfully completed loan.\n");

	}




	@Override
	public String toString() {
		
		return getFullName()+": "+"Phone: "+phoneNum+", Birth Date: "+birthDate+", "+credentials+
				", Monthly Income: "+monthlyIncome+
				"\n"+account;

	}




	public Account getAccount() {
		return account;
	}



	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFullName()
	{
		return firstName+" "+lastName;
	}


	public Credentials getCredentials() {
		return credentials;
	}


	public double getMonthlyIncome() {
		return monthlyIncome;
	}
	
	
	

}
