package kn_14_5.obykhvost.usermanagement.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DaoFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetUserDao() {
		try
		{
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory instance is null", daoFactory);
			UserDao userDao = daoFactory.getUserDao();
			assertNotNull("UserDao instance is null", userDao);
		}
		catch(RuntimeException re)
		{
			re.printStackTrace();
			fail(re.toString());
		}
	}

}
