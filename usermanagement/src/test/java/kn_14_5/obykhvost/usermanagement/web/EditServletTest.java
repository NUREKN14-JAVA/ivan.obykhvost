package kn_14_5.obykhvost.usermanagement.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import kn_14_5.obykhvost.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	@Test
	public void testEdit() {
		Date date = new Date();
		try 
		{
			date = (new SimpleDateFormat("dd.MM.yyyy")).parse((new SimpleDateFormat("dd.MM.yyyy")).format(date));
		}
		catch (ParseException ex) 
		{
			throw new RuntimeException(ex);
		}
		User user = new User(new Long(1000), "John", "Doe", date);
		getMockUserDao().expect("update", user);
		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	@Test
	public void testEditEmptyFirstName()
	{
		Date date = new Date();
		try 
		{
			date = (new SimpleDateFormat("dd.MM.yyyy")).parse((new SimpleDateFormat("dd.MM.yyyy")).format(date));
		}
		catch (ParseException ex) 
		{
			throw new RuntimeException(ex);
		}
		User user = new User(new Long(1000), null, "Doe", date);
		addRequestParameter("id", "1000");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testEditEmptyLastName()
	{
		Date date = new Date();
		try 
		{
			date = (new SimpleDateFormat("dd.MM.yyyy")).parse((new SimpleDateFormat("dd.MM.yyyy")).format(date));
		}
		catch (ParseException ex) 
		{
			throw new RuntimeException(ex);
		}
		User user = new User(new Long(1000), "John", null, date);
		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "John");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testEditEmptyDateOfBirth()
	{
		User user = new User(new Long(1000), "John", "Doe", null);
		addRequestParameter("id", "1000");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("firstName", "John");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testEditEmptyDateOfBirthIncorrect()
	{
		User user = new User(new Long(1000), "John", "Doe", null);
		addRequestParameter("id", "1000");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("firstName", "John");
		addRequestParameter("dateOfBirth", "dsfsgfdgdfhddh");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

}
