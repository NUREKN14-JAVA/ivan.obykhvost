package kn_14_5.obykhvost.usermanagement.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import kn_14_5.obykhvost.usermanagement.User;

public class AddServletTest extends MockServletTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	@Test
	public void testAdd() {
		Date date = new Date();
		try 
		{
			date = (new SimpleDateFormat("dd.MM.yyyy")).parse((new SimpleDateFormat("dd.MM.yyyy")).format(date));
		}
		catch (ParseException ex) 
		{
			throw new RuntimeException(ex);
		}
		User newUser = new User("John", "Doe", date);
		User user = new User(new Long(1000), "John", "Doe", date);
		getMockUserDao().expectAndReturn("create", newUser, user);
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	@Test
	public void testAddEmptyFirstName()
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
		addRequestParameter("lastName", "Doe");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testAddEmptyLastName()
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
		addRequestParameter("firstName", "John");
		addRequestParameter("dateOfBirth", (new SimpleDateFormat("dd.MM.yyyy")).format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testAddEmptyDateOfBirth()
	{
		addRequestParameter("lastName", "Doe");
		addRequestParameter("firstName", "John");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	@Test
	public void testAddEmptyDateOfBirthIncorrect()
	{
		addRequestParameter("lastName", "Doe");
		addRequestParameter("firstName", "John");
		addRequestParameter("dateOfBirth", "dsfsgfdgdfhddh");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}

}
