package kn_14_5.obykhvost.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory {

	public DaoFactoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDao getUserDao() {
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
