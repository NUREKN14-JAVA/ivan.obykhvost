package kn_14_5.obykhvost.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kn_14_5.obykhvost.usermanagement.User;

public class DetailsPanel extends JPanel implements ActionListener{
	private MainFrame parent;
	private JPanel fieldPanel;
	private JPanel buttonsPanel;
	private JButton okButton;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField dateOfBirthField;
	private Color bgColor;

	public DetailsPanel(MainFrame parent, User user) {
		this.parent = parent;
		initialize(user);
	}

	private void initialize(User user) {
		setName(Messages.getString("DetailsPanel.detailsPanel")); //$NON-NLS-1$
		setLayout(new BorderLayout());
		add(getFieldPanel(user.getFirstName(), user.getLastName(), user.getDateOfBirth()), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getFieldPanel(String firstName, String lastName, Date dateOfBirth) {
		if(fieldPanel == null)
		{
			fieldPanel = new JPanel();
			getFirstNameField().setText(firstName);
			fieldPanel.add(getFirstNameField(), null);
			getLastNameField().setText(lastName);
			fieldPanel.add(getLastNameField(), null);
			getDateOfBirthField().setText(dateOfBirth.toString());
			fieldPanel.add(getDateOfBirthField(), null);
		}
		return fieldPanel;
	}
	
	private JPanel getButtonsPanel() {
		if(buttonsPanel == null)
		{
			buttonsPanel = new JPanel();
			buttonsPanel.add(getOkButton(), null);
		}
		return buttonsPanel;
	}

	private JButton getOkButton() {
		if(okButton == null)
		{
			okButton = new JButton();
			okButton.setText("Ok"); //$NON-NLS-1$
			okButton.setName(Messages.getString("DetailsPanel.okButton"));  //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JTextField getDateOfBirthField() {
		if(dateOfBirthField == null)
		{
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName(Messages.getString("DetailsPanel.dateOfBirthField")); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if(lastNameField == null)
		{
			lastNameField = new JTextField();
			lastNameField.setName(Messages.getString("DetailsPanel.lastNameField")); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private JTextField getFirstNameField() {
		if(firstNameField == null)
		{
			firstNameField = new JTextField();
			firstNameField.setName(Messages.getString("DetailsPanel.firstNameField")); //$NON-NLS-1$
		}
		return firstNameField;
	}

	private void clearFields() {
		getFirstNameField().setText(""); //$NON-NLS-1$
		getFirstNameField().setBackground(bgColor);
		getLastNameField().setText(""); //$NON-NLS-1$
		getLastNameField().setBackground(bgColor);
		getDateOfBirthField().setText(""); //$NON-NLS-1$
		getDateOfBirthField().setBackground(bgColor);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		clearFields();
		setVisible(false);
		parent.showBrowsePanel();
	}
}
