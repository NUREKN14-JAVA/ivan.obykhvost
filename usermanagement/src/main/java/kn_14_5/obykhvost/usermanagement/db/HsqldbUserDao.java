package kn_14_5.obykhvost.usermanagement.db;

import java.util.Collection;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import kn_14_5.obykhvost.usermanagement.User;

class HsqldbUserDao implements UserDao {
	final private String SELECT_BY_NAMES = "SELECT * FROM users WHERE firstname = ? AND lastname = ?";
	private ConnectionFactory connectionFactory;
	final private String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	final private String SELECT_ALL_QUERY = "SELECT * FROM users";
	final private String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	final private String SELECT_QUERY = "SELECT * FROM users WHERE id = ?";
	final private String DELETE_QUERY = "DELETE FROM users WHERE id = ?";

	public HsqldbUserDao(){}
	
	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public User create(User user) throws DatabaseException {
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(this.INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate();
			if(n != 1)
			{
				throw new DatabaseException("Number of inserted rows: " + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if(keys.next())
			{
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
	}

	@Override
	public void update(User user) throws DatabaseException {
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(this.UPDATE_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			statement.setLong(4, new Long(user.getId()));
			int n = statement.executeUpdate();
			if(n != 1)
			{
				throw new DatabaseException("Number of updated rows: " + n);
			}
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
	}

	@Override
	public void delete(User user) throws DatabaseException {
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(this.DELETE_QUERY);
			statement.setLong(1, new Long(user.getId()));
			int n = statement.executeUpdate();
			if(n != 1)
			{
				throw new DatabaseException("Number of deleted rows: " + n);
			}
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
	}

	@Override
	public User find(Long id) throws DatabaseException {
		User user = new User();
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(this.SELECT_QUERY);
			statement.setLong(1, new Long(id));
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
			}
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
		return user;
	}

	@Override
	public Collection findAll() throws DatabaseException {
		Collection result = new LinkedList();
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(this.SELECT_ALL_QUERY);
			while(resultSet.next())
			{
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
		return result;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public Collection find(String firstName, String lastName) throws DatabaseException {
		Collection result = new LinkedList();
		try
		{
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAMES);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		}
		catch(DatabaseException de)
		{
			throw de;
		}
		catch(SQLException sqle)
		{
			throw new DatabaseException(sqle);
		}
		return result;
	}

}
