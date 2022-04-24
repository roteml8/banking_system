package banking_system.users;

import java.time.LocalDate;

public class AccountOwner extends Person {
		
	protected Account account;
	protected double monthlyIncome;
	protected Credentials credentials;
	
	public AccountOwner(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate,
			double monthlyIncome) {
		super(firstName, lastName, phoneNum, birthDate);
		this.monthlyIncome = monthlyIncome;
	}
	
	

}
