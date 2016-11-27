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
import javax.swing.JTextField;

import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;
import kn_14_5.obykhvost.usermanagement.util.Messages;

public class AddPanel extends JPanel implements ActionListener{
	private MainFrame parent;
	private JPanel buttonsPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	
	public AddPanel(MainFrame parent)
	{
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		setName("addPanel"); //$NON-NLS-1$
		setLayout(new BorderLayout());
		add(getFieldPanel(), BorderLayout.NORTH);
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
			cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName("cancelButton"); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if(okButton == null)
		{
			okButton = new JButton();
			okButton.setText(Messages.getString("AddPanel.ok")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
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
			addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	private JTextField getDateOfBirthField() {
		if(dateOfBirthField == null)
		{
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if(lastNameField == null)
		{
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
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
		if("ok".equalsIgnoreCase(e.getActionCommand()))
		{
			User user = new User();
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat formatter = DateFormat.getDateInstance();
			try
			{
				user.setDateOfBirth(formatter.parse(getDateOfBirthField().getText()));
				parent.getDao().create(user);
			}
			catch(ParseException pe)
			{
				getDateOfBirthField().setBackground(Color.RED);
			}
			catch(DatabaseException de)
			{
				JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		clearFields();
		setVisible(false);
		parent.showBrowsePanel();
	}

	private void clearFields() {
		getFirstNameField().setText("");
		getFirstNameField().setBackground(bgColor);
		getLastNameField().setText("");
		getLastNameField().setBackground(bgColor);
		getDateOfBirthField().setText("");
		getDateOfBirthField().setBackground(bgColor);
	}
}