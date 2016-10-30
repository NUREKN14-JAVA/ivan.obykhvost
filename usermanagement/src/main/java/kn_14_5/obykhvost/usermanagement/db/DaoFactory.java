package kn_14_5.obykhvost.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	final private Properties properties;
	final private String USER_DAO = "dao.kn_14_5.obykhvost.usermanagement.db.UserDao";
	final private String CON_DRIVER = "connection.driver";
	final private String CON_URL = "connection.url";
	final private String CON_USER = "connection.user";
	final private String CON_PASSWORD = "connection.password";
	static private final DaoFactory INSTANCE = new DaoFactory();
	
	public static DaoFactory getInstance() { return INSTANCE; }

	private DaoFactory() {
		properties = new Properties();
		try
		{
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		}
		catch(IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
	}
	
	private ConnectionFactory getConnectionFactory()
	{
		String driver = this.properties.getProperty(this.CON_DRIVER);
		String url = this.properties.getProperty(this.CON_URL);
		String user = this.properties.getProperty(this.CON_USER);
		String password = this.properties.getProperty(this.CON_PASSWORD);
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
	
	public UserDao getUserDao()
	{
		UserDao result = null;
		try
		{
			Class clazz = Class.forName(properties.getProperty(this.USER_DAO));
			result = (UserDao)(clazz.newInstance());
			result.setConnectionFactory(getConnectionFactory());
		}
		catch(ClassNotFoundException cnfe)
		{
			throw new RuntimeException(cnfe);
		}
		catch(IllegalAccessException iae)
		{
			throw new RuntimeException(iae);
		}
		catch(InstantiationException ie)
		{
			throw new RuntimeException(ie);
		}
		return result;
	}

}
