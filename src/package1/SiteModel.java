package package1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**********************************************************************
 * The following class is responsible for maintaining the database and 
 * updating the GUI. 
 * 
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class SiteModel extends AbstractTableModel {

	/** ArrayList that stores the Sites */
	private ArrayList<Site> listSites;

	/** Array of the column names */
	private String[] columnNames = { "Name Reserving", "Checked-In", 
			"Days Staying", "Check-out", "Site #", "Tent/RV Info" };

	/******************************************************************
	 * Constructor that calls the super() method to get all the methods
	 * from the AbstractTableMethod. It also creates an empty ArrayList
	 * of Sites.
	 *****************************************************************/
	public SiteModel() {
		super();
		listSites = new ArrayList<Site>();
	}

	/******************************************************************
	 * Method that returns that estimated length of the campers stay.
	 * @param s Site used to get information.
	 * @return double of Estimated days staying on the campsite.
	 *****************************************************************/
	public int stayLength(Site s) {
		return s.dayCount();
	}

	/******************************************************************
	 * Gets the current estimated cost of the site.
	 * @param s Site used for getting information.
	 * @return double of estimated cost.
	 *****************************************************************/
	public double getTotal(Site s) {
		return s.cost;
	}

	/*****************************************************************
	 * This is an ArrayList method that returns all of the sites 
	 * in the campground.
	 * @return ArrayList<Site> of the current sites.
	 ****************************************************************/
	public ArrayList<Site> getListSites() {
		return listSites;
	}

	/******************************************************************
	 * Void method that sets the listSites Array to its parameter.
	 * @param listSites ArrayList<Site> that sets the current Array.
	 *****************************************************************/
	public void setListSites(ArrayList<Site> listSites) {
		this.listSites = listSites;
	}

	/******************************************************************
	 * Site Method that returns a Site that matches its parameter.
	 * @param i Index of listSite
	 * @return Site that matches i.
	 *****************************************************************/
	public Site getSite(int i) {
		return listSites.get(i);
	}

	/******************************************************************
	 * This method returns the size of the ArrayList<Site> listSites.
	 * @return int of the size of the array.
	 *****************************************************************/
	public int getSize() {
		return listSites.size();
	}

	/******************************************************************
	 * This method adds a site to the array and updates the GUI.
	 * @param s New site to add to the list and GUI.
	 *****************************************************************/
	public void addSite(Site s) {
		listSites.add(s);
		fireTableRowsInserted(0, listSites.size());
	}

	/******************************************************************
	 * This method removes a site from the GUI and the Array.
	 * @param i int of site to be removed.
	 *****************************************************************/
	public void removeSite(int i) {
		listSites.remove(i);
		fireTableRowsDeleted(i, i);
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
		return listSites.size();
	}

	/******************************************************************
	 * Method updates the gui.
	 * @param row of the table to update 
	 * @param col of the row in the table
	 * @return The value to update to the table.
	 *****************************************************************/
	@Override
	public Object getValueAt(int row, int col) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		switch (col) {
		case 0:
			return listSites.get(row).getNameReserving();
		case 1:
			return format.format(listSites.get(row).getCheckIn().
					getTime());
		case 2:
			return listSites.get(row).getDaysStaying();
		case 3:
			return format.format(listSites.get(row).getCheckOut().
					getTime());
		case 4:
			return listSites.get(row).getSiteNumber();
		case 5:
			if (listSites.get(row) instanceof RV) {
				return "Power: " + ((RV) listSites.get(row)).getPower()
						+ " amps";
			}
			else
				return "Tenters : " + ((Tent) listSites.get(row)).
						getNumOfTenters();
		default:
			return null;
		}
	}

	/******************************************************************
	 * This method returns the name of the column.
	 * @param col Value of the column name to return.
	 * @return String of the column name.
	 *****************************************************************/
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/******************************************************************
	 * This method saves the current Sites in the listSites array to a 
	 * Serializable file.
	 ******************************************************************/
	public void saveSerial() {
		try {

			// gets a file from the getOtherFile Method
			File file = getOtherFile("save");
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream siteOut = new ObjectOutputStream(out);
			siteOut.writeObject(listSites);
			siteOut.close();
		}

		// if the user closes the window or there is an error 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No File Saved");
		}
	}

	/******************************************************************
	 * Method loads a serializable file and updates the GUI and array.
	 * The current Display is cleared and replaced with the loaded
	 * file.
	 ******************************************************************/
	public void loadSerial() {
		try {

			// get file from getOtherFile method
			File file = getOtherFile("load");
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream siteIn = new ObjectInputStream(in);

			// listSites is updated from file
			listSites = (ArrayList<Site>) siteIn.readObject();
			fireTableRowsInserted(0, listSites.size() - 1);
			in.close();
		}

		// thrown if user closese window or there is an error
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No File Loaded");
		}
	}

	/******************************************************************
	 * Method that returns a file to be loaded or saved. Adds .ser to 
	 * the end of the file to show it is a serializable file.
	 * @param type String of save or load
	 * @return File to be loaded or saved.
	 *****************************************************************/
	private File getOtherFile(String type) {
		JFileChooser choose = new JFileChooser();
		int answer = -1;
		if (type.equals("save")) {

			// opens save dialog
			answer = choose.showSaveDialog(null);
		}
		else {

			// opens close dialog
			answer = choose.showOpenDialog(null);
		}

		// gets desired file
		File file = choose.getSelectedFile();
		String path = file.getPath();

		// if user clicks save
		if (answer == 0) {
			if (!(path.contains(".ser"))) {
				if (path.contains(".")) {
					int endIndex = path.lastIndexOf('.');
					path = path.substring(0, endIndex);
				}
				path += ".ser";
			}
		}
		return new File(path);
	}

	/******************************************************************
	 * Method that returns a file to be loaded or saved. Adds .txt to 
	 * the end of the file to show it is a text file. If it is any 
	 * other file type it attempts to replace it with .txt.
	 * @param type String of save or load
	 * @return File to be loaded or saved.
	 *****************************************************************/
	private File getTextFile(String type) {
		JFileChooser choose = new JFileChooser();
		int answer = -1;
		if (type.equals("save")) {

			// opens save file dialog
			answer = choose.showSaveDialog(null);
		}
		else {

			// opens open file dialog
			answer = choose.showOpenDialog(null);
		}
		File file = choose.getSelectedFile();
		String path = file.getPath();

		// if open or save is clicked
		if (answer == 0) {
			if (!(path.contains(".txt"))) {
				if (path.contains(".")) {
					int endIndex = path.lastIndexOf('.');
					path = path.substring(0, endIndex);
				}
				path += ".txt";
			}
		}
		return new File(path);
	}

	/******************************************************************
	 * Method saves the current information to a text file.
	 *****************************************************************/
	public void saveText() {
		try {

			// get file 
			File saveFile = getTextFile("save");
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(saveFile)));

			// loops through every site in list
			for (int i = 0; i < listSites.size(); i++) {
				Site save = listSites.get(i);
				out.println(save.nameReserving);
				out.println(save.siteNumber);

				// print out the date in the format "MM/dd/yyyy"
				out.println(DateFormat.getDateInstance(DateFormat.
						SHORT).format(save.checkIn.getTime()));	
				out.println(save.daysStaying);
				out.println(DateFormat.getDateInstance(DateFormat.
						SHORT).format(save.checkOut.getTime()));
				out.println(save.getClass().getName());

				// saves the power if RV, people if tent
				if (save instanceof RV) {
					out.println(((RV) save).getPower());
				}
				else {
					out.println(((Tent) save).getNumOfTenters());
				}
			}
			out.close();
		}

		// user closes the windows or error occurs
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No File Saved");
		}
	}

	/******************************************************************
	 * Method loads the saved Sites from a Text File. The current sites
	 * are lost if they are not saved before the file is loaded.
	 *****************************************************************/
	public void loadText() {
		try {
			File loadFile = getTextFile("load");

			// deletes tables
			fireTableRowsDeleted(0, listSites.size());

			// clear listSites
			listSites.clear();
			Scanner in = new Scanner(loadFile);
			GregorianCalendar inDate = null;
			GregorianCalendar outDate = null;
			DateFormat format = new SimpleDateFormat("MM/dd/yy");
			try {

				// loop runs until there are no more lines to read
				while (in.hasNext()) {
					inDate = new GregorianCalendar();
					outDate = new GregorianCalendar();
					String name = in.nextLine();
					int site = Integer.parseInt(in.nextLine());
					Date chkIn = format.parse(in.nextLine());
					inDate.setTime(chkIn);
					int length = Integer.parseInt(in.nextLine());
					Date chkOut = format.parse(in.nextLine());
					outDate.setTime(chkOut);
					String type = in.nextLine();

					// reads power or total people
					int powOrTotal = Integer.parseInt(in.nextLine());

					//  saves site as RV or Tent
					if (type.contains("RV")) {
						RV rv = new RV(name, inDate, length, outDate,
								site, powOrTotal);
						listSites.add(rv);
					}
					else {
						Tent tent = new Tent(name, inDate, length,
								outDate, site, powOrTotal);
						listSites.add(tent);
					}
				}
				in.close();

				// update the GUI
				fireTableRowsInserted(0, listSites.size() - 1);
			}

			// Error while loading file
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error: Corrupt"
						+ " file, Data lost!");
			}
		}

		// User closes the file or another error occurs
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No File Loaded.");
		}
	}
}
