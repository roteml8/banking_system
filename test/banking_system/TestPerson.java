package banking_system;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import banking_system.users.Person;
import banking_system.users.PhoneNumber;

class TestPerson {
	
	private Person person;
	

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void TestConstructor() {
		LocalDate birthDate = LocalDate.of(1994, 8, 8);
		PhoneNumber phoneNum = new PhoneNumber("052", "5360337");
		person = new Person("Rotem","Levi",phoneNum, birthDate);
		assertNotNull(person);
		assertEquals(birthDate, person.getBirthDate());
		assertEquals(phoneNum, person.getPhoneNum());
		assertEquals("Rotem", person.getFirstName());
		assertEquals("Levi", person.getLastName());
		
	}
	
	@Test
	void testSetPhoneAndBirthDate()
	{
		PhoneNumber phoneNum = new PhoneNumber("050","123456");
		person = new Person(phoneNum);
		LocalDate bDate = LocalDate.of(2022, 5, 10);
		person.setBirthDate(bDate);
		assertEquals(bDate,person.getBirthDate());
		assertEquals(phoneNum, person.getPhoneNum());

	}
	
	@Test
	void testSetFirstAndLast()
	{
		String first = "Rotem!!", last = "levi1";
		PhoneNumber phoneNum = new PhoneNumber("050","123456");
		person = new Person(phoneNum);
		person.setFirstName(first);
		person.setLastName(last);
		assertEquals("Rotem", person.getFirstName());
		assertEquals("levi", person.getLastName());
		person.setFirstName("Yaron");
		person.setLastName("shender");
		assertEquals("Yaron", person.getFirstName());
		assertEquals("shender", person.getLastName());
	}

}
