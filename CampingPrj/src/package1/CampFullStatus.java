package package1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**********************************************************************
 * The following class inherits the SiteModel class and displays the 
 * camp status information. Two of the fields are different than the 
 * original site.
 * 
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class CampFullStatus extends SiteModel implements
ActionListener {
	
	/** Table that displays the information */
	private JTable table;
	
	/** Scroll pane needed by the JTable */
	private JScrollPane scrollPane;
	
	/** Submits the form */
	private JButton btnOK;

	/** JDialog that is displayed */
	private JDialog dialog;
	
	/** Stores the current sites */
	private ArrayList<Site> sites;
	
	/** Stores the new column names */
	private String[] columnNames = { "Name Reserving", "Checked In",
			"Site #", "Estimated Days", "Days Remaining" };
	
	/******************************************************************
	 * Constructor that Creates a JDialog and displayed the camp status
	 * information.
	 * @param parent JFrame that the dialog is displayed in
	 * @param sites ArrayList of the taken sites.
	 *****************************************************************/
	public CampFullStatus(JFrame parent, ArrayList<Site> sites) {
		super();
		sites = new ArrayList<Site>(sites);
		dialog = new JDialog(parent);
		dialog.setTitle("Campground Status");
		table = new JTable(this);
		scrollPane = new JScrollPane(table);
		dialog.add(scrollPane);
		JPanel bottom = new JPanel();
		dialog.getContentPane().add(bottom, BorderLayout.SOUTH);
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setHorizontalAlignment(SwingConstants.RIGHT);
		bottom.add(btnOK);
		
		// add the sites to display
		addSites(sites);
		dialog.setSize(750,300);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	/******************************************************************
	 * This method adds the sites to the display.
	 * @param sites Site information
	 *****************************************************************/
	public void addSites(ArrayList<Site> sites) {
		for (int i = 0; i < sites.size(); i++) {
			addSite(sites.get(i));
		}
	}
	
	/******************************************************************
	 * This method returns the string of the column name.
	 * @param col int of the column
	 * @return String of the column name
	 *****************************************************************/
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/******************************************************************
	 * This method returns the length of the columnNames array.
	 * @return int of the Length of the columnNames array
	 *****************************************************************/
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/******************************************************************
	 * Method returns the size of the listSites array.
	 * @return int of the size of the listSites array
	 *****************************************************************/
	@Override 
	public int getRowCount() {
		return getListSites().size();
	}
	
	/******************************************************************
	 * Method updates the gui. This overrides the method from the 
	 * SiteModel class.
	 * @param row of the table to update 
	 * @param col of the row in the table
	 * @return The value to update to the table.
	 *****************************************************************/
	@Override
	public Object getValueAt(int row, int col) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Site unit = getSite(row);
		switch (col) {
		case 0:
			return unit.getNameReserving();
		case 1:
			return format.format(unit.getCheckIn());
		case 2:
			return unit.getSiteNumber();
		case 3:
			return unit.getDaysStaying();
		case 4:
			return unit.dayCount();
		default:
			return null;
		}
	}
	
	/******************************************************************
	 * This method is responsible for closing the dialog if the ok 
	 * button is clicked.
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		JComponent event = (JComponent) e.getSource();
		if (event == btnOK) {
			
			// close the form
			dialog.dispose();
		}
	}
}
