package banking_system.users;

import java.util.Objects;

/**
 * a class that represents a phone number by area code and number
 * @author Rotem
 *
 */
public class PhoneNumber {
	
	protected int areaCode;
	protected int number;
	
	public PhoneNumber(int areaCode, int number) {
		this.areaCode = areaCode;
		this.number = number;
	}
	
	public String toString()
	{
		String str = String.format("%03d", areaCode);
		str += "-";
		str += String.format("%07d", number);
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
		return areaCode == other.areaCode && number == other.number;
	}

	
}
