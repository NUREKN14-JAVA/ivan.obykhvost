package kn_14_5.obykhvost.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.KeyEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DaoFactory;
import kn_14_5.obykhvost.usermanagement.db.DaoFactoryImpl;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;
import kn_14_5.obykhvost.usermanagement.db.MockDaoFactory;
import kn_14_5.obykhvost.usermanagement.db.MockUserDao;

public class MainFrameTest extends JFCTestCase {
	private MainFrame mainFrame;

	private Mock mockUserDao;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.setProperty("dao.factory", MockDaoFactory.class.getName());
		DaoFactory.init(properties);
		mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
		mockUserDao.expectAndReturn("findAll", new ArrayList<User>());
		setHelper(new JFCTestHelper());
		try
		{
			mainFrame = new MainFrame();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@After
	protected void tearDown() throws Exception {
		try
		{
			mockUserDao.verify();
			mainFrame.setVisible(false);
			TestHelper.cleanUp(this);
			super.tearDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Component find(Class componentClass, String name)
	{
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}
	
	@Test
	public void testBrowseControls()
	{
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("Имя", table.getColumnName(1));
		assertEquals("Фамилия", table.getColumnName(2));
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	@Test
	public void testAddUser()
	{
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		User user = new User(firstName, lastName, now);
		User expectedUser = new User(new Long(2), firstName, lastName, now);
		mockUserDao.expectAndReturn("create", user, expectedUser);
		ArrayList<User> users = new ArrayList<User>();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		int rowCount = table.getRowCount();
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class, "addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		find(JButton.class, "cancelButton");
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount() - rowCount);
	}
	
	@Test
	public void testEditUser()
	{
		JTable table = (JTable) find(JTable.class, "userTable");
		ArrayList<User> users = new ArrayList<User>();
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		User user = new User(firstName, lastName, now);
		users.add(user);
		table.setModel(new UserTableModel(users));
		firstName = "William";
		lastName = "Shakespeare";
		users.get(0).setFirstName(firstName);
		users.get(0).setLastName(lastName);
		table.setRowSelectionInterval(0, 0);
		int rowCount = table.getRowCount();
		JButton editButton = (JButton) find(JButton.class, "editButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
		find(JPanel.class, "editPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		find(JButton.class, "cancelButton");
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		User expectedUser = new User(new Long(0), firstName, lastName, now);
		mockUserDao.expect("update", expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(rowCount, table.getRowCount());
	}
	
	@Test
	public void testDeleteUser()
	{
		JTable table = (JTable) find(JTable.class, "userTable");
		ArrayList<User> users = new ArrayList<User>();
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		User user = new User(firstName, lastName, now);
		users.add(user);
		table.setModel(new UserTableModel(users));
		users.remove(user);
		table.setRowSelectionInterval(0, 0);
		int rowCount = table.getRowCount();
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
		int selectedRow = table.getSelectedRow();
		User expectedUser = new User();
		expectedUser.setId(new Long(0));
		mockUserDao.expect("delete", expectedUser);
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
		List dialogs = new DialogFinder(null).findAll();
		AbstractButtonFinder abf = new AbstractButtonFinder("Yes");
		List buttons = abf.findAll((JDialog)dialogs.get(0));
		mockUserDao.expectAndReturn("findAll", users);
		getHelper().enterClickAndLeave(new MouseEventData(this, (JButton)buttons.get(0)));
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, rowCount - table.getRowCount());
	}
	
	@Test
	public void testUserDetails()
	{
		JTable table = (JTable) find(JTable.class, "userTable");
		ArrayList<User> users = new ArrayList<User>();
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		User user = new User(firstName, lastName, now);
		mockUserDao.expectAndReturn("find", new Long(0), user);
		users.add(user);
		table.setModel(new UserTableModel(users));
		table.setRowSelectionInterval(0, 0);
		JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
		find(JPanel.class, "detailsPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		assertEquals((String)table.getValueAt(0, 1), firstNameField.getText());
		assertEquals((String)table.getValueAt(0, 2), lastNameField.getText());
	}

}
