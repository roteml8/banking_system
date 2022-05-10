package banking_system.users;

import java.util.Objects;

/**
 * a class that represents a phone number by area code and number
 * @author Rotem
 *
 */
public class PhoneNumber {
	
	protected String areaCode;
	protected String number;
	
	public PhoneNumber(String areaCode, String number) {
		setAreaCode(areaCode);
		setNumber(number);
	}
	
	public String toString()
	{
		String str = String.format("%s-%s", areaCode, number);
		return str;
	}



	@Override
	public int hashCode() {
		return Objects.hash(areaCode, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneNumber other = (PhoneNumber) obj;
		return Objects.equals(areaCode, other.areaCode) && Objects.equals(number, other.number);
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	
	
}
