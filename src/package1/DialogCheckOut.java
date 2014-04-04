package package1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.toedter.calendar.JDateChooser;

/**********************************************************************
 * The following class is used for creating a new Tent site. This class
 * uses two external libraries as well. It also uses a Property Change
 * listener that updates the price when the user changes the check out
 * date.
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class DialogCheckOut extends JDialog implements ActionListener,
PropertyChangeListener {

	/** JTextField containing the credit card number */
	private JTextField txtCardNumber;

	/** textfield containing the total amount of cash*/
	private JTextField txtCash;

	/** radio button for cash*/
	private JRadioButton radCash;

	/** radio button for credit card */
	private JRadioButton radCard;

	/** Puts cash and creditcard button together **/
	private ButtonGroup group;

	/** Stores types of credit cards */
	private JComboBox cmbType;

	/** stores expiration months */
	private JComboBox cmbExpMonth;

	/** stores expiration years */
	private JComboBox cmbExpYear;

	/** title of total days */
	private JLabel lblDays;

	/** title of cash total */
	private JLabel lblTotal;

	/** submits form */
	private JButton btnOK;

	/** cancels form */
	private JButton btnCancel;

	/** Site that is checking out */
	private Site site;

	/** Current Date */
	private Date date;

	/** Date chooser that allows user to select date */
	private JDateChooser pick;

	/** result of check out */
	public static final int OK = 0;

	/** check out canceled */ 
	public static final int CANCEL = 1;

	/** result of check out */
	private int result;

	/******************************************************************
	 * Constructor creates a new JDialog that allows the user to check
	 * out of a site.
	 * @param parent Frame displayed in
	 * @param site Site to check out.
	 *****************************************************************/
	public DialogCheckOut(JFrame parent, Site site) {
		super(parent, true);
		this.site = site;
		result = CANCEL;
		
		// currentdate
		date = startDate();
		setBounds(100, 100, 280, 402);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		setTitle("Check-out");
		
		// start up check out
		JLabel lblTitle = new JLabel("Check-out");
		lblTitle.setFont(new Font("Cooper Black", Font.PLAIN, 16));
		lblTitle.setBounds(85, 11, 93, 14);
		panel.add(lblTitle);
		JLabel lblName = new JLabel("Name: " + site.nameReserving);
		lblName.setBounds(10, 40, 192, 14);
		panel.add(lblName);
		
		// radiobutton allowing user to pay with cash
		radCash = new JRadioButton("Cash");
		radCash.setHorizontalAlignment(SwingConstants.CENTER);
		radCash.setBounds(31, 171, 97, 15);
		panel.add(radCash);
		
		// set as selected
		radCash.setSelected(true);
		radCash.addActionListener(this);
		lblDays = new JLabel("Days: " + site.getDaysStaying());
		lblDays.setBounds(10, 110, 192, 14);
		panel.add(lblDays);
		lblTotal = new JLabel("Total: " + totalDisplay(site.cost));
		lblTotal.setBounds(10, 125, 192, 14);
		panel.add(lblTotal);
		txtCardNumber = new JTextField();
		txtCardNumber.setBounds(10, 205, 244, 20);
		panel.add(txtCardNumber);
		
		// radiobutton that allows the user to pay with a card
		radCard = new JRadioButton("Card");
		radCard.setHorizontalAlignment(SwingConstants.CENTER);
		radCard.setBounds(134, 171, 97, 15);
		panel.add(radCard);
		radCard.addActionListener(this);

		// adds two radiobuttons to a group so they function together
		group = new ButtonGroup();
		group.add(radCash);
		group.add(radCard);
		JLabel lblPayment = new JLabel("Payment Method: ");
		lblPayment.setFont(new Font("Tahoma", Font.BOLD |
				Font.ITALIC, 11));
		lblPayment.setBounds(78, 150, 107, 14);
		panel.add(lblPayment);
		txtCash = new JTextField();
		txtCash.setBounds(10, 300, 244, 20);
		panel.add(txtCash);

		JLabel lblCardNumber = new JLabel("Card Number:");
		lblCardNumber.setBounds(10, 190, 186, 14);
		panel.add(lblCardNumber);
		JLabel lblTotalCash = new JLabel("Total Cash:");
		lblTotalCash.setBounds(10, 282, 192, 14);
		panel.add(lblTotalCash);
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(10, 225, 93, 14);
		panel.add(lblType);
		JLabel lblExpiration = new JLabel("Expiration:");
		lblExpiration.setBounds(140, 225, 93, 14);
		panel.add(lblExpiration);

		// Possible card types
		String[] cardTypes = { "Visa", "Mastercard", "Discovery", 
		"American Express" };
		cmbType = new JComboBox();
		cmbType.setModel(new DefaultComboBoxModel(cardTypes));
		cmbType.setBounds(10, 242, 97, 20);
		panel.add(cmbType);
		String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", 
				"9", "10", "11", "12" };
		cmbExpMonth = new JComboBox();
		cmbExpMonth.setModel(new DefaultComboBoxModel(months));
		cmbExpMonth.setBounds(140, 242, 56, 20);
		panel.add(cmbExpMonth);
		String[] years = { "2013", "2014", "2015", "2016", "2017" };
		cmbExpYear = new JComboBox(new DefaultComboBoxModel(years));
		cmbExpYear.setBounds(198, 242, 56, 20);
		panel.add(cmbExpYear);
		JLabel lblCheckout = new JLabel("Check-out Date:");
		lblCheckout.setBounds(10, 62, 192, 14);
		panel.add(lblCheckout);

		// allows user to select a date
		pick = new JDateChooser();
		pick.setDateFormatString("MM/dd/yyyy");
		
		// startdate
		pick.setMinSelectableDate(date);
		
		// 5 years from now
		pick.setMaxSelectableDate(setBounds(5, date));
		pick.setBounds(10, 79, 244, 20);
		pick.setDate(date);
		panel.add(pick);
		
		// updates price informatoin based on check out date
		pick.addPropertyChangeListener(this);


		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnOK = new JButton("Check-out");
		getRootPane().setDefaultButton(btnOK);
		buttonPanel.add(btnOK);
		btnCancel = new JButton("Cancel");
		buttonPanel.add(btnCancel);
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		updateDisplay();
		
		// stops credit card fields from being selected
		setFields("Cash");
		setVisible(true);
	}

	/******************************************************************
	 * This method returns the result of the check-out.
	 * @return int result
	 *****************************************************************/
	public int getResult() {
		return result;
	}

	/******************************************************************
	 * Method returns the days between two dates
	 * @return number of days between two dates
	 *****************************************************************/
	private int daysBetween(Date in, Date out, int compare) {
		DateTime begin = new DateTime(in);
		DateTime end = new DateTime(out);
		int count = Days.daysBetween(begin, end).getDays();
		
		// the next day returns 0, so return 1
		if (count == 0 && compare == -1) {
			return 1;
		}
		
		// multiply by -1 because a negative day is returned
		else if (count < 0) {
			return count *= -1;
		}
		else {
			return count;
		}
	}

	/******************************************************************
	 * Method displays the cost in a currency format.
	 * @return String of cost
	 *****************************************************************/
	private String totalDisplay(double cost) {
		NumberFormat val = new DecimalFormat("0.00");
		return "$" + val.format(cost);
	}
	
	/******************************************************************
	 * Method determines the starting day for the class.
	 * @return Starting date
	 *****************************************************************/
	public Date startDate() {
		int compare = compareDates(today(), site.checkIn.getTime());
		
		// if the checkIn date is less than today, start at the check
		// in date
		if (compare < 0) {
			return site.checkIn.getTime();
		}
		else {
			return today();
		}
	}

	/******************************************************************
	 * Method returns the current date
	 * @return Current date
	 *****************************************************************/
	private Date today() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get((Calendar.YEAR));
		int month = cal.get((Calendar.MONTH));
		int day = cal.get((Calendar.DAY_OF_MONTH));
		cal.clear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	/******************************************************************
	 * Method sets the min and max of the JDateChooser
	 * @return max/min date
	 *****************************************************************/
	private Date setBounds(int years, Date today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/******************************************************************
	 * Method turns off all the comboboxes and textbox that deal with 
	 * a credit card.
	 * @param onOff Turns them on and off
	 *****************************************************************/
	private void cardInfo(boolean onOff) {
		txtCardNumber.setEnabled(onOff);
		cmbType.setEnabled(onOff);
		cmbExpYear.setEnabled(onOff);
		cmbExpMonth.setEnabled(onOff);
	}

	/******************************************************************
	 * Method turns off all the comboboxes and textbox that deal with 
	 * a cash
	 * @param onOff True turns on false turns off.
	 *****************************************************************/
	private void cashInfo(boolean onOff) {
		txtCash.setEditable(onOff);
	}

	/******************************************************************
	 * This method switches the cash and credit card payment options.
	 * @param selection "Cash" sets the cash fields on, card off
	 *****************************************************************/
	private void setFields(String selection) {
		if (selection.equals("Cash")) {
			cardInfo(false);
			cashInfo(true);
		}
		else {
			cardInfo(true);
			cashInfo(false);
		}
	}

	/******************************************************************
	 * This method compares two dates and returns an integer.
	 * @param begin Start compare date
	 * @param leave End compare date
	 * @return 0 if the dates are equal, 1 if above, -1 below
	 *****************************************************************/
	private int compareDates(Date begin, Date leave) {
		return begin.compareTo(leave);
	}

	/******************************************************************
	 * Method updates the JDialog days staying and cost.
	 * @param days Updates the dialog price and stay length
	 *****************************************************************/
	public void updateSite(int days) {
		site.setDaysStaying(days);
		site.setCost(days);
		updateDisplay();
	}

	/******************************************************************
	 * This method updates the two JLabel's for days staying and the 
	 * total.
	 *****************************************************************/
	private void updateDisplay() {
		lblDays.setText("Days: " + site.daysStaying);
		lblTotal.setText("Total: " + totalDisplay(site.cost));
	}

	/******************************************************************
	 * This method erases a JTextField and sends the focus to it.
	 * @param field Field that is reset
	 *****************************************************************/
	private void reset(JTextField field) {
		field.setText("");
		field.requestFocus();
	}

	/******************************************************************
	 * This method checks to see if the cash total is valid.
	 * @param field Field that checks the cash value
	 * @return True if the cash total is valid
	 *****************************************************************/
	private boolean checkCash(JTextField field) {
		String info = field.getText().trim();
		if (!(radCash.isSelected())) {
			return true;
		}
		try {
			if (site.cost == 0.0) {
				return true;
			}
			double cashIn = Double.parseDouble(info);
			
			// if cash is below 0
			if (cashIn < 0) {
				throw new Exception();
			}
		}
		
		// error
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Please"
					+ " enter a valid cash amount.");
			reset(field);
			return false;
		}
		return true;
	}

	/******************************************************************
	 * This method checks for a valid credit card number.
	 * @param field JTextField that checks the credit card number
	 * @return True if the credit card number is valid
	 *****************************************************************/
	private boolean checkCardNumber(JTextField field) {
		
		// card is not selected return true
		if (!(radCard.isSelected())) {
			return true;
		}
		String info = field.getText().trim();
		
		// if the card length is not 16
		if (info.length() != 16) {
			JOptionPane.showMessageDialog(null, "Error: Card must"
					+ " be 16 digits.");
			reset(field);
			return false;
		}
		
		// checks each character for a letter
		for (char c : info.toCharArray()) {
			if (!(c == '1' || c == '2' || c == '3' || c == '4' || 
					c == '5' || c == '6' || c == '7' || c == '8' || 
					c == '9' || c == '0')) {
				reset(field);
				JOptionPane.showMessageDialog(null, "Error: Card"
						+ "must be 16 numbers.");
				return false;
			}
		}
		return true;
	}

	/******************************************************************
	 * This method checks if the choosen date is within the range.
	 * @return True if the date is valid
	 *****************************************************************/
	private boolean confirmDate() {
		
		// date selected
		Date testDate = pick.getDate();
		
		// minimum date
		Date minDate = pick.getMinSelectableDate();
		
		// maximum date
		Date maxDate = pick.getMaxSelectableDate();
		try {
			int min = testDate.compareTo(minDate);
			int max = testDate.compareTo(maxDate);
			
			// date out of range
			if (min < 0 || max > 0) {
				throw new Exception();
			}
			int days = Days.daysBetween(new DateTime(today()),
					new DateTime(testDate)).getDays();
			if (days == 0) {
				return true;
			}
			else {
				
				// confirms the date
				int confirm = JOptionPane.showConfirmDialog(null,
						"Check-out on a day other than today?",
						"Confirm",
						JOptionPane.YES_NO_OPTION);
				if (confirm == 0) {
					return true;
				}
				
				// reset to today
				else {
					pick.setDate(new Date());
					return false;
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid Date: Must "
					+ "greater than check-in date.");
			pick.setDate(new Date());
			return false;
		}
	}
	
	/******************************************************************
	 * This method returns the total change given back to the person.
	 * @param totalCash
	 * @return double String of the total cash
	 *****************************************************************/
	private double getChange(String totalCash) {
		double value = Double.parseDouble(totalCash);
		return value - site.cost;
	}
	
	/******************************************************************
	 * 
	 * @return True if all the fields are valid and false if one is not
	 *****************************************************************/
	private boolean valid() {
		if (confirmDate() && checkCardNumber(txtCardNumber) && 
				checkCash(txtCash)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/******************************************************************
	 * This method gets all the credit card information.
	 * @return String of credit card information
	 *****************************************************************/
	private String getCardInfo() {
		String cardNumber = txtCardNumber.getText();
		String type = cmbType.getSelectedItem().toString();;
		String month = cmbExpMonth.getSelectedItem().toString();
		String year = cmbExpYear.getSelectedItem().toString();
		
		// returns the last four numbers of the card
		cardNumber = "**** - **** - **** - " +
				cardNumber.substring(12);
		return type + ":\n" + cardNumber + "\n" + month + "\t" + year;
	}
	
	/******************************************************************
	 * This method returns the total cash needed to pay the full price,
	 * or gives the total change needed.
	 * @return String of the total cash 
	 *****************************************************************/
	private String getCashInfo() {
		String cash = txtCash.getText().trim();
		double change;
		if (site.cost == 0.0) {
			
			// sets the change to 0 if the user does not owe anything
			change = 0.0;
		}
		else {
			change = getChange(cash);
		}
		String total = totalDisplay(change);
		if (change < 0) {
			return total + " needed to pay full price.";
		}
		else {
			return "Change: " + total;
		}
	}
	
	/******************************************************************
	 * This method displays a thank you message for either the total 
	 * change or the credit card info.
	 *****************************************************************/
	private void display() {
		String message = "";
		if (radCash.isSelected()) {
			message += getCashInfo();
		}
		else {
			message += getCardInfo();
		}
		message += "\nThank you " + site.nameReserving + "!";
		JOptionPane.showMessageDialog(null, message);
	}

	/******************************************************************
	 * This method controls the actionlistener.
	 * @param e ActionEvent from the radio buttons or JButtons.
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		JComponent event = (JComponent) e.getSource();
		
		// try converting the event to radiobutton
		try {
			JRadioButton radio = (JRadioButton) event;
			boolean selected = radio.isSelected();
			String button = radio.getText();
			
			// set the correct fields
			if (selected && button.equals("Cash")) {
				setFields(button);
			}
			else {
				setFields(button);
			}
		}
		
		// JButton selected
		catch (Exception er) {
			if (event == btnOK) {
				
				// payment valid so the user can check out
				if (valid()) {
					result = OK;
					display();
					dispose();
				}
			}
			
			// check out canceled
			else if (event == btnCancel) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"Cancel Check-out?",
						"Exit", JOptionPane.YES_NO_OPTION);
				if (confirm == 0) {
					dispose();
				}
			}
			
			// error
			else {
				JOptionPane.showMessageDialog(null, "Error");
				dispose();
			}
		}
	}

	/******************************************************************
	 * The method checks for an a change in the JDateChooser. This
	 * method creates two calendars and compares them, it then updates
	 * the display to the correct number of days and price.
	 * @param e
	 *****************************************************************/
	public void propertyChange(PropertyChangeEvent e) {
		JDateChooser choose = (JDateChooser) e.getSource();
		
		// creates a calendar set to today
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(site.checkIn.getTime());
		int sYear = cStart.get(Calendar.YEAR);
		int sMonth = cStart.get(Calendar.MONTH);
		int sDay = cStart.get(Calendar.DAY_OF_MONTH);
		cStart.clear();
		cStart.set(sYear, sMonth, sDay);
		
		// creates a calendar set to the date choosen
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(choose.getDate());
		int eYear = cEnd.get(Calendar.YEAR);
		int eMonth = cEnd.get(Calendar.MONTH);
		int eDay = cEnd.get(Calendar.DAY_OF_MONTH);
		cEnd.clear();
		cEnd.set(eYear, eMonth, eDay);
		Date startDate = cStart.getTime();
		Date calDate = cEnd.getTime();
		
		// compare the two dates
		int compare = compareDates(startDate, calDate);
		
		// get the days between them
		int days = daysBetween(startDate, calDate, compare);
		
		// update the display
		updateSite(days);
	}
}