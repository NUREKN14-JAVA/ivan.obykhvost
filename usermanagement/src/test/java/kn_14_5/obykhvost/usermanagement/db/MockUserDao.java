package kn_14_5.obykhvost.usermanagement.db;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import kn_14_5.obykhvost.usermanagement.User;

public class MockUserDao implements UserDao {
	private Long id = (long) 0;
	private Map users = new HashMap();
	
	public MockUserDao()
	{
		
	}
	
	@Override
	public User create(User user) throws DatabaseException {
		Long currentId = new Long(id++);
		user.setId(currentId);
		users.put(currentId, user);
		return user;
	}

	@Override
	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
		users.put(currentId, user);
	}

	@Override
	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}

	@Override
	public User find(Long id) throws DatabaseException {
		return (User) users.get(id);
	}

	@Override
	public Collection findAll() throws DatabaseException {
		return users.values();
	}

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection find(String firstName, String lastName) throws DatabaseException {
		throw new UnsupportedOperationException();
	}

}
