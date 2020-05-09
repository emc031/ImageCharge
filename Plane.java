
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    Represents a 2D plane on which charges can exist, and can return the electrostatic
    potential due to the charges at any point on the plane. 
    V is in units of 1/4*pi*epsilon-naught joules
 */

import java.util.ArrayList;

public class Plane extends ArrayList<Charge> {

	//constructor
	public Plane () {
		super();
	}
	
	//returns the electrostatic potential at a point on the plane
	public double V ( int x, int y ) {
		
		Charge c;
		
		double v = 0;
		
		for ( int i = 0; i < this.size(); i++ ) {
			//each charge contributes a term of q/r, charge/distance ( if it is "on" )
			c = this.get( i );
			
			if ( c.On() ) {
				v += ( c.GetCharge() )/( c.distance( x, y ) );
			}
		}
		
		return v;
	}
	
	
}
