

/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    Generic panel which creates a visual representation of the plane. Must be extended
    to define charges positions etc, to create the "levels".
    
    The animation shows the charges and level surfaces expressing the potential around them.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class PlanePanel extends JPanel 
					implements MouseListener, MouseMotionListener, ChangeListener {

	//fields
	protected Plane p; //the plane object being represented by the frame
	
	protected final int width = 500; //width of simulation
	protected final int height = 500; //height of simulation
	
	//the index of the charge that is currently being dragged, -1 means no dragging is taking place
	protected int drag = -1;
	
	//an instance of the ImageFrame object which contains the plane
	protected ImageFrame a;
	
	//constructor
	public PlanePanel ( ImageFrame a ) {
		this.a = a;
		p = new Plane();
		AddCharges();
		AddButtons();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	//override paint component
	public void paintComponent ( Graphics g ) {
		g.clearRect( 0, 0, 700, 500 );
		g.fillRect( 499, 0, 2, 500 ); //separation between simulation and buttons
		PaintLines( g );
		PaintCharges( g );
	}
	
	/*add buttons on the side of the plane,
	 takes an instance of the ImageApplet which contains the panel, so that it can
	 be added to the buttons as an action listener */
	protected abstract void AddButtons();
	
	//define charges on the plane
	protected abstract void AddCharges();
	
	//paint level surfaces, this is abstract as different levels will have different conductor surfaces
	protected abstract void PaintLines ( Graphics g );
	
	//paint charges
	protected void PaintCharges ( Graphics g ) {
		
		Charge c;
		int r;
		
		for ( int i = 0; i < p.size(); i++ ) {
			c = p.get( i );
			r = c.GetRadius();
			
			//painting a white space so the charge does not have lines close to it
			g.setColor( Color.white );
			g.fillOval( (int)( c.getX() - 25 ), (int)( c.getY() - 25 ), 50, 50 );
			
			//chosing colour for charge depending on +ve or -ve charge
			if ( c.GetCharge() > 0 ) { g.setColor( Color.red ); }
			if ( c.GetCharge() < 0 ) { g.setColor( Color.blue ); }
			
			//painting charge
			g.fillOval( (int)( c.getX() - r ), (int)( c.getY() - r ), r*2, r*2 );
			
			//painting sign on the charge,
			g.setColor( Color.black );
			
			g.fillRect( (int)( c.getX() - r/2 ), (int)( c.getY() - r/8 ), r, r/4 );
			
			if ( c.GetCharge() > 0 ) {
				g.fillRect( (int)( c.getX() - r/8 ), (int)( c.getY() - r/2 ), r/4, r );
			}
			
		}
		
	}
	
	
	//event handling
	
	
	//reaction to a charge being pressed, the charge is now being "held" by the user
	public void mousePressed ( MouseEvent e ) {
		
		Charge c;
		Point ep = e.getPoint();
		
		//test if each of the charge was pressed, AND if charge is mobile, set drag to this charge
		for ( int i = 0; i < p.size(); i++ ) {
			
			c = p.get( i );
			
			if ( ep.distance( c ) <= c.GetRadius() ) {
				if ( c.Mobile() ){
					drag = i;
					c.setLocation( ep );
				}
			}
			
			repaint();
		}
		
	}
	
	//reaction to a charge being dragged by the user
	public void mouseDragged ( MouseEvent e ) {
		
		//checking one of the charges has been "picked up"
		if ( drag != -1 ) {
			
			Charge c = p.get( drag );
			
			//drag to next position
			c.setLocation( e.getPoint() );
			
			c.CheckSwitch();
		}
	
		repaint();
	}
	
	//unused event methods
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void stateChanged(ChangeEvent e ) {}
	public void mouseReleased(MouseEvent e) {}
	
}
