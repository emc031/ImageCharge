
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    The first PlanePanel subclass describing the first problem:
    
    A single positive charge close to a flat surface of a conductor. 
    The user must chose an image charge and place it on the simulation
    such that the conductor surface is a level surface.
 */

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;


public class Level1 extends PlanePanel {

	//fields
	private JButton l2; //buttons leading to the other levels
	private JButton l3;
	
	private JButton reset; //puts all charges into original position
	private JSlider chargeSlider; //slider which takes in chosen charge of image
	private JLabel successMessage; //message that appears when user wins
	
	private final double targetCharge = -4; //target charge for image to have
	private final Point targetPoint = new Point( 250, 375 ); //target point for image to have
	
	private boolean success = false; //set to true if the target charge & point is reached,
									//and commands paintComponent to paint success reaction
	
	
	//constructor
	public Level1 ( ImageFrame a ) {
		super( a );
	}
	
	//getters for level buttons
	
	public JButton GetL2Button() {
		return l2;
	}
	
	public JButton GetL3Button() {
		return l3;
	}
	
	protected void AddButtons () {
		
		setLayout( null );
		
		JLabel l1 = new JLabel( "Problem 1" );
		l1.setBounds( 570, 10, 100, 40 );
		add( l1 );
	
		l2 = new JButton( "Problem 2" );
		l2.setBounds( 550, 60, 100, 40 );
		l2.addActionListener( a );
		add( l2 );
		
		l3 = new JButton( "Problem 3" );
		l3.setBounds( 550, 110, 100, 40 );
		l3.addActionListener( a );
		add( l3 );
		
		reset = new JButton( "Reset" );
		reset.setBounds( 550, 450, 100, 40 );
		reset.addMouseListener( this );
		add( reset );
		
		JLabel physical = new JLabel( " physical region" );
		physical.setBounds( 0, 215, 100, 40 );
		add( physical );
		
		JLabel unphysical = new JLabel( " unphysical region" );
		unphysical.setBounds( 0, 245, 150, 40 );
		add( unphysical );
		
		JLabel image = new JLabel( "  Image Charge" );
		image.setBounds( 550, 310, 150, 40 );
		add( image );
	
		chargeSlider = new JSlider( -6, 6, -2 );
		chargeSlider.setBounds( 530, 380, 140, 40 );
		chargeSlider.setPaintTicks( true );
		chargeSlider.setMinorTickSpacing( 1 );
		chargeSlider.setMajorTickSpacing( 6 );
		chargeSlider.addChangeListener( this );
		add( chargeSlider );
		
		successMessage = new JLabel( "poisson's equation is now satisfied in the physical region" );
		successMessage.setBounds( 80, 400, 400, 30 );
		successMessage.setVisible( false );
		add( successMessage );
	}

	protected void AddCharges() {
		
		Charge real = new Charge( 250, 125, 4 ); //the real charge
		real.SetMobile( false );
		
		Charge image = new Charge( 600, 365, -2 ); //the image charge
		
		p.add( real );
		p.add( image ); 
		
	}
	
	protected void PaintLines(Graphics g) {
		
		//drawing conducting surface
		g.fillRect(  0, 248, 500, 4 );
			
			//drawing field lines in physical region (bold)
			for ( int x = 1; x < width; x++ ) {
				for ( int y = 1; y < height/2; y++ ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
		
		if ( success == false ) {
			
			//drawing field lines in unphysical region (light)
			for ( int x = 1; x < width; x += 2 ) {
				for ( int y = height/2; y < height; y += 2 ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
			
		}
	
	}


	private void SuccessCheck() {
		
		if ( p.get( 1 ).GetCharge() == targetCharge && p.get( 1 ).distance( targetPoint ) < 5 ) {
			
			//reaction to the success: show message and paitn 	
			successMessage.setVisible( true );
			success = true;
			repaint();
			
		} else {
			success = false;
			successMessage.setVisible( false );
		}
	}
	
	
	//event handling
	
	//reaction to the reset button being pressed
	public void mouseClicked( MouseEvent e ) {
		if ( e.getSource() == reset ) {
			p.clear();
			AddCharges();
			chargeSlider.setValue( -2 );
			success = false;
			successMessage.setVisible( false );
			repaint();
		}
	}

	//reaction to charge slider being slid
	public void stateChanged( ChangeEvent e ) {
		p.get( 1 ).SetCharge( ( (JSlider)( e.getSource() ) ).getValue() );
		SuccessCheck();
		repaint();
	}
	
	//reaction when charge is dropped, restore drag to its null value
	public void mouseReleased ( MouseEvent e ) {
		drag = -1;
		SuccessCheck();
	}
	
	
}
