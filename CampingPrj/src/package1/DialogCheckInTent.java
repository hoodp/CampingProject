package package1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.toedter.calendar.JDateChooser;

/**********************************************************************
 * The following class is used for creating a new Tent site. This class 
 * uses two external libraries. The JCalendar library allows the user
 * to easily select a date, the Joda Time library is also used.
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class DialogCheckInTent extends JDialog implements
ActionListener {

	/** JLabel for name of the Camper*/
	private JLabel lblName;

	/** Label of the site number */
	private JLabel lblSiteNumber;

	/** Label of occupy date */
	private JLabel lblOccupyDate;

	/** Length of stay */
	private JLabel lblStayLength;

	/** Title for the number of people */
	private JLabel lblTotalPeople;

	/** ComboBox that display the available sites */
	private JComboBox cmbSites;

	/** Allows the user to select a date easily */
	private JDateChooser choose;

	/** JTextField for entering the site name */
	private JTextField txtName;

	/** Length of the stay */
	private JTextField txtStayLength;

	/** Gets the total number of people */
	private JTextField txtTotalPeople;

	/** Submits the form */
	private JButton btnOK;

	/** Closes the form */
	private JButton btnCancel;

	/** Tent site that is created*/
	private Tent unit;

	/** result of the status */
	private int result;

	/** Tent was made successfully */
	public static final int OK = 0;

	/** Tent was not created */
	public static final int CANCEL = 1;

	/** Holds all the sites taken*/
	public ArrayList<Site> takenSites;

	/** String that displays the sites taken */
	private String[] openings;

	/******************************************************************
	 * Constructor creates a JDialog Box, allowing the user to select a 
	 * site, stay length and the power needed.
	 * @param parent JFrame to display JDialog in 
	 * @param d Tent from GUI
	 * @param sites Sites taken
	 *****************************************************************/
	public DialogCheckInTent(JFrame parent, Tent d, ArrayList<Site>
	sites) {
		super(parent, true);
		takenSites = new ArrayList<Site>(sites);
		unit = d;
		result = CANCEL;
		setBounds(100, 100, 228, 300);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		setTitle("Tent Check-in");

		// starts the name area of the GUI
		lblName = new JLabel("Name of Reserver:");
		lblName.setBounds(10, 11, 192, 14);
		panel.add(lblName);
		txtName = new JTextField("Paul Hood");
		txtName.setBounds(10, 30, 192, 20);
		panel.add(txtName);
		lblSiteNumber = new JLabel("Requested site number:");
		lblSiteNumber.setBounds(10, 55, 192, 14);
		panel.add(lblSiteNumber);
		openings = availSites();

		// combobox displays the available sites that the user can 
		// choose from
		cmbSites = new JComboBox();
		cmbSites.setModel(new DefaultComboBoxModel(openings));
		cmbSites.setBounds(10, 74, 192, 22);
		panel.add(cmbSites);

		lblOccupyDate = new JLabel("Occupied on Date:");
		lblOccupyDate.setBounds(10, 99, 192, 14);
		panel.add(lblOccupyDate);

		// date chooser
		choose = new JDateChooser();
		choose.setDateFormatString("MM/dd/yyyy");
		choose.setBounds(10, 118, 192, 20);

		// sets the default date to the current date
		choose.setDate(new Date());
		
		// min is 5 years before today
		choose.setMinSelectableDate(setBounds(-5, choose.getDate()));
		
		// max is 5 years from now
		choose.setMaxSelectableDate(setBounds(5, choose.getDate()));
		panel.add(choose);

		lblStayLength = new JLabel("Days planning on staying:");
		lblStayLength.setBounds(10, 143, 192, 14);
		panel.add(lblStayLength);
		txtStayLength = new JTextField("1");
		txtStayLength.setColumns(10);
		txtStayLength.setBounds(10, 162, 192, 20);
		panel.add(txtStayLength);

		// number of people staying
		lblTotalPeople = new JLabel("Number of Tenters:");
		lblTotalPeople.setBounds(10, 187, 192, 14);
		panel.add(lblTotalPeople);
		txtTotalPeople = new JTextField("5");
		txtTotalPeople.setColumns(10);
		txtTotalPeople.setBounds(10, 206, 192, 20);
		panel.add(txtTotalPeople);

		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnOK = new JButton("OK");
		
		// submits the form if the enter button is pressed
		getRootPane().setDefaultButton(btnOK);
		buttonPanel.add(btnOK);
		btnCancel = new JButton("Cancel");
		buttonPanel.add(btnCancel);
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);		
	}

	/******************************************************************
	 * This method is responsible for displaying the available sites. 
	 * The ComboBox DefaultComboBox method requires an String[] to set 
	 * its values correctly. 
	 * @return Array string of available sites.
	 *****************************************************************/
	private String[] availSites() {
		
		// create new ArrayList of strings
		ArrayList<String> openings = new ArrayList<String>();
		
		// only 5 sites available
		for (int i = 1; i <= 5; i++) {
			boolean taken = false;
			for (Site s : takenSites) {
				
				// if site is already taken break out of loop
				if (s.siteNumber == i) {
					taken = true;
					break;
				}
			}
			
			// if not taken add to ArrayList
			if (!taken) {
				openings.add("" + i);
			}
		}
		
		// create new String[] array the size of ArrayList
		String[] returnOpen = new String[openings.size()];
		
		// add each value to String array
		for (int i = 0; i < openings.size(); i++) {
			returnOpen[i] = openings.get(i);
		}
		return returnOpen;
	}

	/******************************************************************
	 * This method returns the status of the site. 0 if the RV was 
	 * created, 1 if it was canceled.
	 * @return Status of the RV 
	 *****************************************************************/
	public int getResult() {
		return result;
	}

	/******************************************************************
	 * This method is responsible for setting the result.
	 * @param result Sets the result 
	 *****************************************************************/
	public void setResult(int result) {
		this.result = result;
	}

	/******************************************************************
	 * This method resets a JTextField if an error occured. It clears
	 * out the text, sends the focus to it, and reset's its text to the
	 * default.
	 * @param field JTextField where error occured.
	 * @param message String that contains error message to display.
	 * @param text Resets to default value.
	 * @return Updated JTextField
	 *****************************************************************/
	private JTextField resetText(JTextField field, String message,
			String text) {
		JOptionPane.showMessageDialog(null, "Error: " + message);
		field.setText(text);
		field.requestFocus();
		field.selectAll();
		return field;
	}

	/******************************************************************
	 * This method return the current date. 
	 * @return Current Date
	 *****************************************************************/
	private Date today() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		// clear the calendar to reset values like Milliseconds and
		// hours that are also stored in cal.
		cal.clear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	/******************************************************************
	 * This method returns the number of years from todays date. It is 
	 * used to set the min and the max for the Date Chooser.
	 * @param years The number of years to set the min/max
	 * @param time The current date
	 * @return max or minimum date the user can select
	 *****************************************************************/
	private Date setBounds(int years, Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/******************************************************************
	 * This checks to see if the date is within the min and max range. 
	 * If the choosen date is not equal to today, it asks the user to 
	 * confirm the choice.
	 * @return True if the date is valid and within the range
	 *****************************************************************/
	private boolean confirmDate() {
		
		// selected date
		Date testDate = choose.getDate();
		
		// minimum allowed date
		Date minDate = choose.getMinSelectableDate();
		
		// max allowed date
		Date maxDate = choose.getMaxSelectableDate();
		try {
			int min = testDate.compareTo(minDate);
			int max = testDate.compareTo(maxDate);
			
			// if date is less than min or more than max throw error
			if (min < 0 || max > 0) {
				throw new Exception();
			}
			
			// gets days between dates
			int days = Days.daysBetween(new DateTime(today()),
					new DateTime(testDate)).getDays();
			
			// day is today
			if (days == 0) {
				return true;
			}
			
			// day is not today, asks the user if they are sure they
			// want to check in on a day other than today
			else {
				int confirm = JOptionPane.showConfirmDialog(null,
						"Check-in on a day other than today?",
						"Confirm",
						JOptionPane.YES_NO_OPTION);
				
				// user confirms date
				if (confirm == 0) {
					return true;
				}
				else {
					choose.setDate(new Date());
					return false;
				}
			}
		}
		
		// error message and the date is reset to day
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Date: Must "
					+ "be within 5 years.");
			choose.setDate(new Date());
			return false;
		}
	}

	/******************************************************************
	 * This method ensures the name isn't to long and it's length is 
	 * less than 30 and greater than 0.
	 * @param field JTextField where name is entered
	 * @return True if name is valid
	 *****************************************************************/
	private boolean checkName(JTextField field) {
		try {
			int length = field.getText().trim().length();
			
			// length is less 1 or greater than 30
			if (length < 1 || length > 30) {
				throw new Exception();
			}
		}
		
		// return false and reset Textbox
		catch (Exception e) {
			resetText(field, "Enter a valid name", "");
			return false;
		}
		return true;
	}

	/******************************************************************
	 * Methods checks for a valid number of people at the site and a
	 * valid integer for the number of days staying. The site can only 
	 * be occupied for 5 years.
	 * @param field JTextField that contains integer value to check
	 * @return True if the JTextField Contains a valid integer.
	 *****************************************************************/
	private boolean checkInts(JTextField field) {
		if (field == txtTotalPeople) {
			
			// try/catch used to ensure integer is entered
			try {
				int total = Integer.parseInt(txtTotalPeople.getText());
				
				// throw error if number is 0 or greater than 25
				if (total < 1 || total > 25) {
					throw new Exception();
				}
			}
			
			// error message
			catch (Exception e) {
				resetText(field, "Enter a valid # of tenters (1-25)", "1");
				return false;
			}
		}
		else {
			
			// check if the integer is valid and if within time range
			try {
				int length = Integer.parseInt(txtStayLength.getText());
				if (length < 1 || length > (365 * 5)) {
					throw new Exception();
				}
			}
			catch (Exception e) {
				resetText(field, "Enter a valid number of days", "1");
				return false;
			}
		}
		return true;
	}

	/******************************************************************
	 * This method runs through the three validation methods at once.
	 * @return True if all boolean methods are true
	 *****************************************************************/
	private boolean valid() {
		if (checkName(txtName) && checkInts(txtStayLength) && 
				checkInts(txtTotalPeople) && confirmDate()) {
			return true;
		}
		return false;
	}

	/******************************************************************
	 * This method checks to see if the data is valid, then sets the 
	 * RV "unit" to the values in the JDialog box.
	 * @param e Click event from one of the two buttons
	 *****************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button == btnOK) {
			if (valid()) {
				SimpleDateFormat format = new SimpleDateFormat(
						"MM/dd/yyyy");
				Date date = new Date();
				unit.nameReserving = txtName.getText();
				GregorianCalendar chkIn = new GregorianCalendar();
				date = choose.getDate();
				
				// formats the date to display properly
				format.format(date);
				chkIn.setTime(date);
				unit.checkIn = chkIn;
				unit.daysStaying = Integer.parseInt(txtStayLength.
						getText());
				GregorianCalendar chkOut = new GregorianCalendar();
				chkOut.setTime(date);
				
				// adds the estimated number of days staying
				chkOut.add(Calendar.DAY_OF_MONTH, unit.daysStaying);
				unit.checkOut = chkOut;
				unit.siteNumber = Integer.parseInt(cmbSites.
						getSelectedItem().toString());
				unit.setNumOfTenters(Integer.parseInt(
						txtTotalPeople.getText()));
				result = OK;
				NumberFormat val = new DecimalFormat("0.00");
				unit.setCost(unit.daysStaying);
				
				// displays the estimated cost
				String payment = "You owe: $" + val.format(unit.cost);
				JOptionPane.showMessageDialog(null, payment);
				dispose();
			}
		}
		
		// user clicks cancel
		else {
			
			// confirmation
			int confirm = JOptionPane.showConfirmDialog(null,
					"Cancel the RV reservation?",
					"Cancel",
					JOptionPane.YES_NO_OPTION);
			if (confirm == 0) {
				dispose();
			}
		}
	}
}
