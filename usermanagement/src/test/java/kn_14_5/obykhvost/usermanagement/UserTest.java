package kn_14_5.obykhvost.usermanagement;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class UserTest {
	private User user;
	private Date dateOfBirth;

	@Before
	public void setUp() throws Exception { 
		user = new User();
		Calendar calendar = Calendar.getInstance();
		calendar.set(1984, Calendar.MAY, 26);
		dateOfBirth = calendar.getTime();
	}
	
	@Test
	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Doe");
		assertEquals("Doe, John", user.getFullName());
	}
	
	@Test
	public void testGetDateOfBirth()
	{
		user.setDateOfBirth(dateOfBirth);
		Calendar current = Calendar.getInstance(), birth = Calendar.getInstance();
		current.setTime(new Date());
		birth.setTime(dateOfBirth);
		assertEquals(current.get(Calendar.YEAR) - birth.get(Calendar.YEAR), user.getAge());
	}

}