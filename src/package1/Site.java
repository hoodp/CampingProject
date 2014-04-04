package package1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**********************************************************************
 * 
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class Site implements Serializable {

	/** Name of the Person staying */
	protected String nameReserving;

	/** Date of the check-in */
	protected GregorianCalendar checkIn;

	/** Days staying */
	protected int daysStaying;

	/** Estimated Check out out */
	protected GregorianCalendar checkOut;

	/** Site the person is staying on */
	protected int siteNumber;

	/** Cost of the unit */
	protected double cost;

	/******************************************************************
	 * Empty constructor used by the RV and tent class.
	 *****************************************************************/
	public Site() {

	}

	/******************************************************************
	 * The method sets all of the classes fields.
	 * @param nameReserving String name of the person
	 * @param checkIn Check in days
	 * @param daysStaying Estimated Days staying
	 * @param checkOut Date checking out
	 * @param siteNumber Site the user is staying on
	 *****************************************************************/
	public Site(String nameReserving, GregorianCalendar checkIn, 
			int daysStaying, GregorianCalendar checkOut,
			int siteNumber) {
		this.nameReserving = nameReserving;
		this.checkIn = checkIn;
		this.daysStaying = daysStaying;
		this.checkOut = checkOut;
		this.siteNumber = siteNumber;
		cost = 0;
	}

	/******************************************************************
	 * This method returns the cost of the site.
	 * @return double of the cost
	 *****************************************************************/
	public double getCost() {
		return cost;
	}

	/******************************************************************
	 * This method sets the cost of the site. This method is 
 	 * by the inherited classes.
	 * @param days 
	 *****************************************************************/
	public void setCost(int days) {
		cost = days * 1;
	}

	/******************************************************************
	 * This method returns the name of the site.
	 * @return String of the name reserving
	 *****************************************************************/
	public String getNameReserving() {
		return nameReserving;
	}

	/******************************************************************
	 * This method sets the nameReserving field.
	 * @param name of user
	 *****************************************************************/
	public void setNameReserving(String nameReserving) {
		this.nameReserving = nameReserving;
	}

	/******************************************************************
	 * This method returns the check-in date.
	 * @return The check in date;
	 *****************************************************************/
	public Date getCheckIn() {
		return checkIn.getTime();
	}

	/******************************************************************
	 * This method sets the check-in date.
	 * @param Desired check in date
	 *****************************************************************/
	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
	}

	/******************************************************************
	 * This method returns the total days staying.
	 * @return int of the total days staying
	 *****************************************************************/
	public int getDaysStaying() {
		return daysStaying;
	}

	/******************************************************************
	 * This method sets the total days staying.
	 * @param daysStaying Method sets the total days staying.
	 *****************************************************************/
	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}

	/******************************************************************
	 * This is used for estimating the check-out date.
	 * @return The check-out date
	 *****************************************************************/
	public GregorianCalendar getCheckOut() {
		return checkOut;
	}

	/******************************************************************
	 * This method sets the check out date.
	 * @param checkOut The checkout date
	 *****************************************************************/
	public void setCheckOut(GregorianCalendar checkOut) {
		this.checkOut = checkOut;
	}

	/******************************************************************
	 * This method returns the site number.
	 * @return int of the site number
	 *****************************************************************/
	public int getSiteNumber() {
		return siteNumber;
	}

	/******************************************************************
	 * This method sets the user's set number.
	 * @param siteNumber int of the site number
	 *****************************************************************/
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	/******************************************************************
	 * This method returns the current date.
	 * @return Today's date.
	 *****************************************************************/
	public Date currentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.clear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	/******************************************************************
	 * This method returns the number of days between today and the 
	 * estimated check-out date.
	 * @return int of the days between two dates
	 *****************************************************************/
	public int dayCount() {
		DateTime today = new DateTime(currentDate());
		DateTime out = new DateTime(checkOut);
		return Days.daysBetween(today, out).getDays();
	}
}
