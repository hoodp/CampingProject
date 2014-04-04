package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**********************************************************************
 * The following class displays the GUI and allows the user to occupy a 
 * site, check-out from a site and save/load text and serializable
 * files.
 * 
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class GUICampingReg extends JFrame implements ActionListener {

	/** MenuBar that holds the JMenu's */
	private JMenuBar menuBar;

	/** JMenu that holds save and load information */
	private JMenu fileMenu;
	
	/** JMenuItem for the CampStatus */
	private JMenuItem campStatus;

	/** JMenuItem for opening serial files  */
	private JMenuItem openSerial;

	/** JMenuItem for saving serial files */
	private JMenuItem saveSerial;

	/** JMenuItem for opening Text files */
	private JMenuItem openText;

	/** JMenuItem for saving text files */
	private JMenuItem saveText;

	/** JMenuItem for exiting application */
	private JMenuItem exit;

	/** JMenu for checking into camp site */
	private JMenu chkInMenu;

	/** JMenuItem for checking into tent site */
	private JMenuItem chkInTent;

	/** JMenuItem used for checking into RV site */
	private JMenuItem chkInRV;

	/** Holds the Checkout option */
	private JMenu chkOutMenu;

	/** MenuItem for checking out */
	private JMenuItem chkOut;

	/** JTable that displays the information */
	private JTable table;

	/** JScrollPane used by the JTable */
	private JScrollPane scrollPane;

	/** SiteModel used for updating GUI */
	private SiteModel model;

	/******************************************************************
	 * Constructor that sets up the GUI, allowing the User to check in
	 * or out and save files.
	 *****************************************************************/
	public GUICampingReg() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		campStatus = new JMenuItem("Status");
		openSerial = new JMenuItem("Open Serial");
		saveSerial = new JMenuItem("Save Serial");
		openText = new JMenuItem("Open Text");
		saveText = new JMenuItem("Save Text");
		exit = new JMenuItem("Exit!");
		campStatus.addActionListener(this);
		openSerial.addActionListener(this);
		saveSerial.addActionListener(this);
		openText.addActionListener(this);
		saveText.addActionListener(this);
		exit.addActionListener(this);

		// adds the filemenu
		fileMenu.add(campStatus);
		fileMenu.add(openSerial);
		fileMenu.add(saveSerial);
		fileMenu.add(openText);
		fileMenu.add(saveText);
		fileMenu.add(exit);

		// adds the check in menu
		chkInMenu = new JMenu("Checking In");
		chkInTent = new JMenuItem("Check-in Tent Site");
		chkInRV = new JMenuItem("Check-in RV site");
		chkInTent.addActionListener(this);
		chkInRV.addActionListener(this);
		chkInMenu.add(chkInTent);
		chkInMenu.add(chkInRV);

		// adds the check out menu
		chkOutMenu = new JMenu("Check-out");
		chkOut = new JMenuItem("Date left");
		chkOut.addActionListener(this);
		chkOutMenu.add(chkOut);

		// adds the three JMenu
		menuBar.add(fileMenu);
		menuBar.add(chkInMenu);
		menuBar.add(chkOutMenu);

		model = new SiteModel();
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(675,475);
		
		// sets location to the middle of the screen
		setLocationRelativeTo(null);
		setVisible(true);
		updateDisplay();
	}

	/******************************************************************
	 * Methods starts the program.
	 * @param args Arguments that run the GUI
	 *****************************************************************/
	public static void main(String[] args) {
		new GUICampingReg();
	}
	
	/******************************************************************
	 * The following method stops the user from being able to check 
	 * into a site if the campground is full and stops the user from 
	 * saving if there's nothing to save.
	 *****************************************************************/
	public void updateDisplay() {
		int rows = table.getRowCount();
		canSave(rows);
		isFull(rows);
	}
	
	/******************************************************************
	 * Method disables the check In features if the sites are full.
	 * @param rows Number of rows in the table
	 *****************************************************************/
	public void isFull(int rows) {
		if (rows == 5) {
			chkInMenu.setEnabled(false);
			chkInRV.setEnabled(false);
			chkInTent.setEnabled(false);
		}
		else {
			chkInMenu.setEnabled(true);
			chkInRV.setEnabled(true);
			chkInTent.setEnabled(true);
		}
	}

	/******************************************************************
	 * Method disables the save, checkout and campstatus features if 
	 * there is no sites taken.
	 * @param rows int of the number of rows
	 *****************************************************************/
	public void canSave(int rows) {
		if (rows == 0) {
			chkOutMenu.setEnabled(false);
			saveSerial.setEnabled(false);
			saveText.setEnabled(false);
			chkOut.setEnabled(false);
			campStatus.setEnabled(false);
		}
		else {
			saveSerial.setEnabled(true);
			saveText.setEnabled(true);
			chkOut.setEnabled(true);
			campStatus.setEnabled(true);
			chkOutMenu.setEnabled(true);
		}
	}

	/******************************************************************
	 * 
	 * @param e Click event from one of the JMenuButtons
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		JComponent event = (JComponent) e.getSource();
		if (event == exit) {
			
			// int asks if the user wants to exit
			int confirm = JOptionPane.showConfirmDialog(null,
					"Are you sure you wish to exit?",
					"Exit?",
					JOptionPane.YES_NO_OPTION);
			
			// user clicked yes
			if (confirm == 0) {
				System.exit(0);
			}
		}
		
		// campstatus
		else if (event == campStatus) {
			ArrayList<Site> sites = model.getListSites();
			
			// create CampFullStatus and displays it
			CampFullStatus status = new CampFullStatus(this, sites);
		}
		
		// RV check in
		else if (event == chkInRV) {
			RV r = new RV();
			DialogCheckInRv rv = new DialogCheckInRv(this, r,
					model.getListSites());
			
			// if rv result is OK add the site to the GUI
			if (rv.getResult() == rv.OK) {
				model.addSite(r);
			}
		}
		
		// Tent check in
		else if (event == chkInTent) {
			Tent t = new Tent();
			DialogCheckInTent tent = new DialogCheckInTent(this, t,
					model.getListSites());
			
			// tent result is OK, add site to the GUI
			if (tent.getResult() == tent.OK) {
				model.addSite(t);
			}
		}
		
		// save serial file
		else if (event == saveSerial) {
			model.saveSerial();
		}
		else if (event == openSerial) {
			model.loadSerial();
		}
		
		// save text file
		else if (event == saveText) {
			model.saveText();
		}
		else if (event == openText) {
			model.loadText();
		}
		
		// check out site
		else {
			
			// try/catch block used to make sure only 1 row is selected
			try {
				int selections = table.getSelectedRowCount();
				
				// 0 rows or more than 1 throw error
				if (selections != 1) {
					throw new Exception();
				}
				int index = table.getSelectedRow();
				Site site = model.getSite(index);
				DialogCheckOut checkOut = new DialogCheckOut(this,
						site);
				
				// if result is OK remove the site and update GUI
				if (checkOut.getResult() == checkOut.OK) {
					model.removeSite(index);
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: Please"
						+ " select one row.");
				
				// clears all highlighted rows
				table.clearSelection();
			}
		}
		
		// check for full sites or no sites and update
		updateDisplay();
	}
}
