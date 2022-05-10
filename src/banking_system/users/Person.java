package banking_system.users;

import java.time.LocalDate;

/**
 * a class that represents a person and their info
 * @author Rotem
 *
 */
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
	
	public Person(PhoneNumber phoneNum)
	{
		this.phoneNum = phoneNum;
	}

	public void setFirstName(String firstName) {
		this.firstName = trimNonLetters(firstName);
	}

	public void setLastName(String lastName) {

		this.lastName = trimNonLetters(lastName);
	}

	/**
	 * removes non-letter chars from string
	 * @param str the string to be modified
	 * @return
	 */
	public String trimNonLetters(String str)
	{
		String valid = "";
		for (int i = 0; i < str.length(); i++) {
			char current = str.charAt(i);
			if (Character.isLetter(current))
				valid += current;
		}
		return valid;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", phoneNum=" + phoneNum + ", birthDate="
				+ birthDate + "]";
	}

	public PhoneNumber getPhoneNum() {
		return phoneNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	
}
