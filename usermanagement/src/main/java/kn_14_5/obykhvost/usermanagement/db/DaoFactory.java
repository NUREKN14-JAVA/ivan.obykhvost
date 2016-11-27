package kn_14_5.obykhvost.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	private static final String DAO_FACTORY = "dao.factory";
	protected static Properties properties;
	protected final String USER_DAO = "dao.kn_14_5.obykhvost.usermanagement.db.UserDao";
	final private String CON_DRIVER = "connection.driver";
	final private String CON_URL = "connection.url";
	final private String CON_USER = "connection.user";
	final private String CON_PASSWORD = "connection.password";
	static private DaoFactory instance;
	
	static
	{
		properties = new Properties();
		try
		{
			properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		}
		catch(IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
	}
	
	public static synchronized DaoFactory getInstance() 
	{
		if(instance == null)
		{
			try
			{
				Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
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
		}
		return instance; 
	}

	protected DaoFactory() { }
	
	public static void init(Properties prop)
	{
		properties = prop;
		instance = null;
	}
	
	protected ConnectionFactory getConnectionFactory(Properties properties)
	{
		return new ConnectionFactoryImpl(properties);
	}
	
	protected ConnectionFactory getConnectionFactory()
	{
		String driver = this.properties.getProperty(this.CON_DRIVER);
		String url = this.properties.getProperty(this.CON_URL);
		String user = this.properties.getProperty(this.CON_USER);
		String password = this.properties.getProperty(this.CON_PASSWORD);
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
	
	public abstract UserDao getUserDao();

}
