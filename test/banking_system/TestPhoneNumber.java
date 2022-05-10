package banking_system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import banking_system.users.PhoneNumber;

class TestPhoneNumber {

	private PhoneNumber phoneNumber;
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConstructor() {
		String code = "052", number = "5360337";
		phoneNumber = new PhoneNumber(code, number);
		assertNotNull(phoneNumber);
		assertEquals(code, phoneNumber.getAreaCode());
		assertEquals(number, phoneNumber.getNumber());
		
	}
	
	@Test
	void testSetFields()
	{
		phoneNumber = new PhoneNumber("050", "123456");
		phoneNumber.setAreaCode("054");
		phoneNumber.setNumber("987654");
		assertEquals(054, phoneNumber.getAreaCode());
		assertEquals(987654, phoneNumber.getNumber());
	}
	
	@Test
	void testToString()
	{
		phoneNumber = new PhoneNumber("050", "0123456");
		String desc = phoneNumber.toString();
		assertEquals("050-0123456", desc);
	}

}
