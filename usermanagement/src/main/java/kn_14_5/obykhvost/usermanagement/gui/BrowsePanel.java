package kn_14_5.obykhvost.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;
import kn_14_5.obykhvost.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener{

	private MainFrame parent;
	private JPanel buttonsPanel;
	private JButton detailsButton;
	private JButton deleteButton;
	private JButton editButton;
	private JButton addButton;
	private JScrollPane tablePanel;
	private JTable userTable;

	public BrowsePanel(MainFrame frame) {
		parent = frame;
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		setName("browsePanel"); //$NON-NLS-1$
		setLayout(new BorderLayout());
		add(getTablePanel(), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		if(buttonsPanel == null)
		{
			buttonsPanel = new JPanel();
			buttonsPanel.add(getAddButton(), null);
			buttonsPanel.add(getEditButton(), null);
			buttonsPanel.add(getDeleteButton(), null);
			buttonsPanel.add(getDetailsButton(), null);
		}
		return buttonsPanel;
	}

	private JButton getDetailsButton() {
		if(detailsButton == null)
		{
			detailsButton = new JButton();
			detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
			detailsButton.setName("detailsButton"); //$NON-NLS-1$
			detailsButton.setActionCommand("details"); //$NON-NLS-1$
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		if(deleteButton == null)
		{
			deleteButton = new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
			deleteButton.setName("deleteButton"); //$NON-NLS-1$
			deleteButton.setActionCommand("delete"); //$NON-NLS-1$
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}

	private JButton getEditButton() {
		if(editButton == null)
		{
			editButton = new JButton();
		    editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
			editButton.setName("editButton"); //$NON-NLS-1$
			editButton.setActionCommand("edit"); //$NON-NLS-1$
			editButton.addActionListener(this);
		}
		return editButton;
	}

	private JButton getAddButton() {
		if(addButton == null)
		{
			addButton = new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
			addButton.setName("addButton"); //$NON-NLS-1$
			addButton.setActionCommand("add"); //$NON-NLS-1$
			addButton.addActionListener(this);
		}
		return addButton;
	}

	private JScrollPane getTablePanel() {
		if(tablePanel == null)
		{
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if(userTable == null)
		{
			userTable = new JTable();
			userTable.setName("userTable"); //$NON-NLS-1$
		}
		return userTable;
	}

	public void initTable() {
		UserTableModel model = null;
		try
		{
			model = new UserTableModel(parent.getDao().findAll());
		}
		catch(DatabaseException de)
		{
			model = new UserTableModel(new ArrayList());
			JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		userTable.setModel(model);
	}
	
	public void performDelete()
	{
		User user = new User();
		user.setId(new Long(userTable.getSelectedRow()));
		try
		{
			parent.getDao().delete(user);
		}
		catch(DatabaseException de)
		{
			JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		parent.showBrowsePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if("add".equalsIgnoreCase(actionCommand)) //$NON-NLS-1$
		{
			setVisible(false);
			parent.showAddPanel();
		}
		if("edit".equalsIgnoreCase(actionCommand) && userTable.getSelectedRowCount() == 1)
		{
			setVisible(false);
			parent.showEditPanel(userTable.getSelectedRow());
		}
		if("delete".equalsIgnoreCase(actionCommand) && userTable.getSelectedRowCount() == 1)
		{
			setVisible(false);
			if(JOptionPane.showConfirmDialog(this, "Подтверждение") == JOptionPane.OK_OPTION)
			{
				performDelete();
			}
		}
		if("details".equalsIgnoreCase(actionCommand) && userTable.getSelectedRowCount() == 1)
		{
			setVisible(false);
			parent.showDetailsPanel(userTable.getSelectedRow());
		}
	}

}

















