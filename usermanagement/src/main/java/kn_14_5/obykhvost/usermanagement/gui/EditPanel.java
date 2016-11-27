package kn_14_5.obykhvost.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;

public class EditPanel extends JPanel implements ActionListener{
	private MainFrame parent;
	private JPanel buttonsPanel;
	private int rowToEdit;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	
	public EditPanel(MainFrame parent, int rowNumber)
	{
		this.parent = parent;
		this.rowToEdit = rowNumber;
		initialize();
	}

	private void initialize() {
		setName(Messages.getString("EditPanel.editPanel")); //$NON-NLS-1$
		setLayout(new BorderLayout());
		add(getFieldPanel(), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getButtonsPanel() {
		if(buttonsPanel == null)
		{
			buttonsPanel = new JPanel();
			buttonsPanel.add(getOkButton(), null);
			buttonsPanel.add(getCancelButton(), null);
		}
		return buttonsPanel;
	}

	private JButton getCancelButton() {
		if(cancelButton == null)
		{
			cancelButton = new JButton();
			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setName(Messages.getString("EditPanel.cancelButton")); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if(okButton == null)
		{
			okButton = new JButton();
			okButton.setText("Ok"); //$NON-NLS-1$
			okButton.setName(Messages.getString("EditPanel.okButton"));  //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if(fieldPanel == null)
		{
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new BorderLayout());
			addLabeledField(fieldPanel, Messages.getString("EditPanel.firstName"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("EditPanel.lastName"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("EditPanel.dateOfBirth"), getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private JTextField getDateOfBirthField() {
		if(dateOfBirthField == null)
		{
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName(Messages.getString("EditPanel.dateOfBirthField")); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if(lastNameField == null)
		{
			lastNameField = new JTextField();
			lastNameField.setName(Messages.getString("EditPanel.lastNameField")); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private JTextField getFirstNameField() {
		if(firstNameField == null)
		{
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if("ok".equalsIgnoreCase(e.getActionCommand())) //$NON-NLS-1$
		{
			User user = new User();
			user.setId(new Long(rowToEdit));
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat formatter = DateFormat.getDateInstance();
			try
			{
				user.setDateOfBirth(formatter.parse(getDateOfBirthField().getText()));
				parent.getDao().update(user);
			}
			catch(ParseException pe)
			{
				getDateOfBirthField().setBackground(Color.RED);
			}
			catch(DatabaseException de)
			{
				JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
		clearFields();
		setVisible(false);
		parent.showBrowsePanel();
	}

	private void clearFields() {
		getFirstNameField().setText(""); //$NON-NLS-1$
		getFirstNameField().setBackground(bgColor);
		getLastNameField().setText(""); //$NON-NLS-1$
		getLastNameField().setBackground(bgColor);
		getDateOfBirthField().setText(""); //$NON-NLS-1$
		getDateOfBirthField().setBackground(bgColor);
	}
}
