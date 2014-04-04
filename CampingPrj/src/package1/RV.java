package package1;

import java.util.GregorianCalendar;

/**********************************************************************
 * This is an inherited class from the Site class. This method sets the
 * cost of the site and the power.
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class RV extends Site {

	/** int that stores the power */
	private int power;

	/******************************************************************
	 * Constructor that calls the super method 
	 *****************************************************************/
	public RV() {
		super();
	}

	/******************************************************************
	 * Constructor that sets the fields of the RV class.
	 * @param nameReserving String name of the person
	 * @param checkIn Check in days
	 * @param daysStaying Estimated Days staying
	 * @param checkOut Date checking out
	 * @param siteNumber Site the user is staying on
	 * @param power Power needed for the site.
	 *****************************************************************/
	public RV(String nameReserving, GregorianCalendar checkIn, 
			int daysStaying, GregorianCalendar checkOut,
			int siteNumber, int power) {
		super(nameReserving, checkIn, daysStaying, checkOut,
				siteNumber);
		this.power = power;
	}

	/******************************************************************
	 * This method returns the power needed on the site.
	 * @return int of the Power needed.
	 *****************************************************************/
	public int getPower() {
		return power;
	}

	/******************************************************************
	 * This method sets the power integer.
	 * @param Sets the power field
	 *****************************************************************/
	public void setPower(int power) {
		this.power = power;
	}

	/******************************************************************
	 * This method overrides the setCost method. It sets the price to a
	 * the correct value for an RV site.
	 * @param days Number of days staying
	 *****************************************************************/
	@Override
	public void setCost(int days) {
		cost = 30 * days;
	}
}