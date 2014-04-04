package package1;

import java.util.GregorianCalendar;

/**********************************************************************
 * This is an inherited class from the Site class. This method sets the
 * cost of the site and the number of people on the site.
 * @author Paul Hood
 * @version 10/2013
 *
 *********************************************************************/
public class Tent extends Site {

	/** int that stores the number of people*/
	private int numOfTenters;

	
	/******************************************************************
	 * Constructor that calls the super method 
	 *****************************************************************/
	public Tent() {
		super();
	}

	/******************************************************************
	 * Constructor that sets the fields of the RV class.
	 * @param nameReserving String name of the person
	 * @param checkIn Check in days
	 * @param daysStaying Estimated Days staying
	 * @param checkOut Date checking out
	 * @param siteNumber Site the user is staying on
	 * @param numOfTenters Number of people staying on the site
	 *****************************************************************/
	public Tent(String nameReserving, GregorianCalendar checkIn, 
			int daysStaying, GregorianCalendar checkOut,
			int siteNumber, int numOfTenters) {
		super(nameReserving, checkIn, daysStaying, checkOut,
				siteNumber);
		this.numOfTenters = numOfTenters;
		cost = daysStaying * 3 * numOfTenters;
	}	

	/******************************************************************
	 * This method returns the number of people on the site.
	 * @return Number of people
	 *****************************************************************/
	public int getNumOfTenters() {
		return numOfTenters;
	}

	/******************************************************************
	 * Sets the number of people on the site
	 * @param numOfTenters Number of people to set
	 *****************************************************************/
	public void setNumOfTenters(int numOfTenters) {
		this.numOfTenters = numOfTenters;
	}

	/******************************************************************
	 * This method overrides the setCost method. It sets the price to a
	 * the correct value for an RV site.
	 * @param days Number of days staying
	 *****************************************************************/
	@Override
	public void setCost(int days) {
		cost = 3 * days * numOfTenters;
	}
}
