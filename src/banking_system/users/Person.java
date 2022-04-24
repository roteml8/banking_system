package banking_system.users;

import java.time.LocalDate;

public class Person {
	
	protected String firstName;
	protected String lastName;
	protected final PhoneNumber phoneNum;
	protected LocalDate birthDate;
	
	public Person(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.birthDate = birthDate;
	}
	
	

}
