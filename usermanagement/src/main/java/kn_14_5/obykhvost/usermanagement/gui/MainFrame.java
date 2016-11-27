package kn_14_5.obykhvost.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DaoFactory;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;
import kn_14_5.obykhvost.usermanagement.db.UserDao;
import kn_14_5.obykhvost.usermanagement.util.Messages;

public class MainFrame extends JFrame {
	
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private UserDao dao;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;
	
	public UserDao getDao() {
		return dao;
	}

	public MainFrame()
	{
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		setContentPane(getContentPanel());
	}

	private JPanel getContentPanel() {
		if(this.contentPanel == null)
		{
			this.contentPanel = new JPanel();
			this.contentPanel.setLayout(new BorderLayout());
			this.contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return this.contentPanel;
	}

	private JPanel getBrowsePanel() {
		if(browsePanel == null)
		{
			browsePanel = new BrowsePanel(this);
		}
		browsePanel.initTable();
		return browsePanel;
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
	
	public void showBrowsePanel()
	{
		showPanel(getBrowsePanel());
	}
	
	public void showAddPanel() {
		showPanel(getAddPanel());
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if(addPanel == null)
		{
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public void showEditPanel(int rowToEdit) {
		showPanel(getEditPanel(rowToEdit));
	}

	private EditPanel getEditPanel(int rowToEdit) {
		if(editPanel == null)
		{
			editPanel = new EditPanel(this, rowToEdit);
		}
		return editPanel;
	}

	public void showDetailsPanel(int selectedRow) {
		showPanel(getDetailsPanel(selectedRow));
	}

	private DetailsPanel getDetailsPanel(int selectedRow) {
		if(detailsPanel == null)
		{
			User user = null;
			try
			{
				user = dao.find(new Long(selectedRow));
			}
			catch(DatabaseException de)
			{
				JOptionPane.showMessageDialog(this, de.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			detailsPanel = new DetailsPanel(this, user);
		}
		return detailsPanel;
	}

}
