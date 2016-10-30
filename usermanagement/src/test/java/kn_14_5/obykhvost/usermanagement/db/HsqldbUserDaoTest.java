package kn_14_5.obykhvost.usermanagement.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import kn_14_5.obykhvost.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase{
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(this.connectionFactory);
	}

	@Test
	public void testCreate() {
		try
		{
			User user = new User();
			user.setFirstName("Donald");
			user.setLastName("Trump");
			Calendar calendar = Calendar.getInstance();
			calendar.set(1946, Calendar.JUNE, 14);
			user.setDateOfBirth(calendar.getTime());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		}
		catch(DatabaseException de)
		{
			de.printStackTrace();
			fail(de.toString());
		}
	}
	
	@Test
	public void testFind()
	{
		try
		{
			User user = dao.find(new Long(1000));
			assertNotNull("No such user", user.getId());
			assertEquals("Wrong id", new Long(1000), user.getId());
			assertEquals("Wrong full name", "Clinton, Hillary", user.getFullName());
		}
		catch(DatabaseException de)
		{
			de.printStackTrace();
			fail(de.toString());
		}
	}
	
	@Test
	public void testDelete()
	{
		try
		{
			User user = dao.find(new Long(1000));
			dao.delete(user);
			user = dao.find(new Long(1000));
			assertNull("User was not deleted", user.getId());
		}
		catch(DatabaseException de)
		{
			de.printStackTrace();
			fail(de.toString());
		}
	}
	
	@Test
	public void testFindAll()
	{
		try
		{
			Collection collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size", 2, collection.size());
		}
		catch(DatabaseException de)
		{
			de.printStackTrace();
			fail(de.toString());
		}
	}
	
	@Test
	public void testUpdate()
	{
		try
		{
			User user = new User();
			user.setFirstName("Abraham");
			user.setLastName("Lincoln");
			Calendar calendar = Calendar.getInstance();
			calendar.set(1861, Calendar.MARCH, 4);
			user.setDateOfBirth(calendar.getTime());
			user.setId(new Long(1000));
			dao.update(user);
			User userUpd = dao.find(new Long(1000));
			Calendar calendarUpd = Calendar.getInstance();
			calendarUpd.setTime(new Date(userUpd.getDateOfBirth().getTime()));
			assertEquals("Wrong full name", user.getFullName(), userUpd.getFullName());
			assertEquals("Wrong year", calendar.get(Calendar.YEAR), calendarUpd.get(Calendar.YEAR));
			assertEquals("Wrong month", calendar.get(Calendar.MONTH), calendarUpd.get(Calendar.MONTH));
			assertEquals("Wrong day", calendar.get(Calendar.DAY_OF_MONTH), calendarUpd.get(Calendar.DAY_OF_MONTH));
		}
		catch(DatabaseException de)
		{
			de.printStackTrace();
			fail(de.toString());
		}
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		this.connectionFactory = new ConnectionFactoryImpl();
		return new DatabaseConnection(this.connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
