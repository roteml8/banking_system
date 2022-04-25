package banking_system.users;

import java.time.LocalDate;

public class Person {
	
	protected String firstName;
	protected String lastName;
	protected final PhoneNumber phoneNum;
	protected LocalDate birthDate;
	
	public Person(String firstName, String lastName, PhoneNumber phoneNum, LocalDate birthDate) {
		setFirstName(firstName);
		setLastName(lastName);
		setBirthDate(birthDate);
		this.phoneNum = phoneNum;
	}

	public void setFirstName(String firstName) {
		firstName.replaceAll("[0-9]", "");
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		lastName.replaceAll("[0-9]", "");
		this.lastName = lastName;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", phoneNum=" + phoneNum + ", birthDate="
				+ birthDate + "]";
	}
	
	
}
