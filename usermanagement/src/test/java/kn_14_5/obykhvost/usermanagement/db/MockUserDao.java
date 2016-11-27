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
		User user = new User();
		user.setId(id);
		user.setFirstName("Vasya");
		user.setLastName("Pupkin");
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.APRIL, 25);
		user.setDateOfBirth(calendar.getTime());
		users.put(id++, user);
		user.setId(id);
		user.setFirstName("Carl");
		user.setLastName("Marx");
		calendar.set(1818, Calendar.MAY, 5);
		user.setDateOfBirth(calendar.getTime());
		users.put(id++, user);
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

}