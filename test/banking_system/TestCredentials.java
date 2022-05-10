package banking_system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import banking_system.users.Credentials;

class TestCredentials {
	
	private Credentials credentials;

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConstructor() {
		String username = "rotemlevi", password = "rotem8";
		credentials = new Credentials(username, password);
		assertNotNull(credentials);
		assertEquals(username, credentials.getUsername());
		assertEquals(password, credentials.getPassword());
	}
	
	@Test
	void testIsUsernameLegal()
	{
		String[] validUsernames = {"Rotem", "rotem", "rotem12", "r455"};
		String[] illegalUsernames = {"rot!", "12!", "7#"};
		boolean[] validResult = {true, true, true, true};
		boolean[] illegalResult = {false, false, false};
		boolean[] actualValidResult = new boolean[validUsernames.length];
		boolean[] actualIllegalResult = new boolean[illegalUsernames.length];
		for (int i=0; i<validUsernames.length; i++)
			actualValidResult[i] = Credentials.isUsernameLegal(validUsernames[i]);
		for (int i=0; i<illegalUsernames.length; i++)
			actualIllegalResult[i] = Credentials.isUsernameLegal(illegalUsernames[i]);
		assertArrayEquals(validResult, actualValidResult);
		assertArrayEquals(illegalResult, actualIllegalResult);
 	}
	
	@Test
	void testIsPasswordLegal()
	{
		String[] validPasswords = {"Rotem1", "rotem1@", "rotem12", "r455"};
		String[] illegalPasswords = {"12", "a12", "rotem12345","abcd"};
		boolean[] validResult = {true, true, true, true};
		boolean[] illegalResult = {false, false, false, false};
		boolean[] actualValidResult = new boolean[validPasswords.length];
		boolean[] actualIllegalResult = new boolean[illegalPasswords.length];
		for (int i=0; i<validPasswords.length; i++)
			actualValidResult[i] = Credentials.isPasswordLegal(validPasswords[i]);
		for (int i=0; i<illegalPasswords.length; i++)
			actualIllegalResult[i] = Credentials.isPasswordLegal(illegalPasswords[i]);
		assertArrayEquals(validResult, actualValidResult);
		assertArrayEquals(illegalResult, actualIllegalResult);
 	}
	

}
