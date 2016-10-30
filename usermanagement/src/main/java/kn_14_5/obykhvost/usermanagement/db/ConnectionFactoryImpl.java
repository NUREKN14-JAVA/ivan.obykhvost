package kn_14_5.obykhvost.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public ConnectionFactoryImpl() 
	{
		this.driver = "org.hsqldb.jdbcDriver";
		this.url = "jdbc:hsqldb:file:db/usermanagement";
		this.user = "sa";
		this.password = "";
	}

	public ConnectionFactoryImpl(String driver, String url, String user, String password) 
	{
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public Connection createConnection() throws DatabaseException {
		try
		{
			Class.forName(this.driver);
		}
		catch(ClassNotFoundException cnfe)
		{
			throw new RuntimeException(cnfe);
		}
		try 
		{
			return DriverManager.getConnection(this.url, this.user, this.password);
		} 
		catch (SQLException sqle) 
		{
			throw new DatabaseException(sqle);
		}
	}

}
