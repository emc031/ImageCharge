
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    Represents a charge at a position on a plane, with a charge "q" and a radius "r"
    which is determined by q
 */

import java.awt.Point;

public class Charge extends Point {

	//fields
	private boolean on = true; //the charge effects the field, i.e. takes part in the simulation
	private boolean mobile = true; //the charge can be dragged
	
	private double q; //charge, keep in range between 6 & -6
	private int r; //radius
	
	//constructors
	
	public Charge ( int x, int y, double charge ) {
		this.x = x;
		this.y = y;
		q = charge;
		r = (int)Math.abs( q*3 );
		CheckSwitch();
	}
	
	public Charge () {
		this.setLocation( 0, 0 );
		q = 1;
		r = 3;
	}
	
	//getters
	
	public double GetCharge() {
		return q;
	}
	
	public int GetRadius() {
		return r;
	}
	
	public boolean On () {
		return on;
	}
	
	public boolean Mobile () {
		return mobile;
	}
	
	//setters
	
	public void SetCharge ( double charge ) {
		this.q = charge;
		r = (int)Math.abs( q*3 );
	}
	
	public void SetMobile ( boolean m ) {
		mobile = m;
	}
	
	public void TurnOn ( boolean o ) {
		on = o;
	}
	
	
	//checks if the charge has been taken out or put into the simulation, and turns on or off accordingly
	public void CheckSwitch () {
		
		//if charge is dragged out of simulation, turn it off
		if ( on ) {
			if ( x > 500  || x < 0 || y > 500 || y < 0 ) {
				on = false;
			}
		}
	
		//if charge is dragged onto simulation, turn it on
		if ( !on ) {
			if ( x < 500 && x > 0 && y < 500 && y > 0 ) {
				on = true;
			}
		}
	}
	
}
