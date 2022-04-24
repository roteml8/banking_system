package banking_system.users;

import java.time.LocalDate;

public class Person {
	
	protected String firstName;
	protected String lastName;
	protected final PhoneNumber phoneNum;
	protected LocalDate birthDate;
	
	public Person(String firstName, String lastName, int areaCode, int number, int day, int month, int year) {
		setFirstName(firstName);
		setLastName(lastName);
		setBirthDate(day, month, year);
		this.phoneNum = new PhoneNumber(areaCode, number);
	}

	public void setFirstName(String firstName) {
		firstName.replaceAll("[0-9]", "");
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		lastName.replaceAll("[0-9]", "");
		this.lastName = lastName;
	}

	public void setBirthDate(int day, int month, int year) {
		this.birthDate = LocalDate.of(year, month, day);
	
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", phoneNum=" + phoneNum + ", birthDate="
				+ birthDate + "]";
	}
	
	
}
