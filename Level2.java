
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    The second PlanePanel subclass describing the second problem:
    
    A single negative charge next to a conducting sphere. The user must place an 
    image charge inside the sphere.
 */

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;


public class Level2 extends PlanePanel {

	//fields
	private JButton l1; //buttons leading to the other levels
	private JButton l3;
	
	private JButton reset; //puts all charges into original position
	private JSlider chargeSlider; //slider which takes in chosen charge of image
	private JLabel successMessage; //message for when user wins level
	
	private final double targetCharge = 2; //target charge for image to have
	private final Point targetPoint = new Point( 245, 250 ); //target point for image to have
	
	private boolean success = false; //set to true if the target charge & point is reached,
									//and commands paintComponent to paint success reaction
	
	//constructor
	public Level2 ( ImageFrame a ) {
		super( a );
	}
	
	//getters for level buttons
	
	public JButton GetL1Button() {
		return l1;
	}
		
	public JButton GetL3Button() {
		return l3;
	}
	
	protected void AddButtons () {
		
		setLayout( null );
		
		l1 = new JButton( "Problem 1" );
		l1.setBounds( 550, 10, 100, 40 );
		l1.addActionListener( a );
		add( l1 );
	
		JLabel l2 = new JLabel( "Problem 2" );
		l2.setBounds( 570, 60, 100, 40 );
		add( l2 );
		
		l3 = new JButton( "Problem 3" );
		l3.setBounds( 550, 110, 100, 40 );
		l3.addActionListener( a );
		add( l3 );
		
		reset = new JButton( "Reset" );
		reset.setBounds( 550, 450, 100, 40 );
		reset.addMouseListener( this );
		add( reset );
		
		JLabel image = new JLabel( "  Image Charge" );
		image.setBounds( 550, 310, 150, 40 );
		add( image );
	
		chargeSlider = new JSlider( -6, 6, 3 );
		chargeSlider.setBounds( 530, 380, 140, 40 );
		chargeSlider.setPaintTicks( true );
		chargeSlider.setMinorTickSpacing( 1 );
		chargeSlider.setMajorTickSpacing( 6 );
		chargeSlider.addChangeListener( this );
		add( chargeSlider );
		
		successMessage = new JLabel( "poisson's equation is now satisfied in the physical region" );
		successMessage.setBounds( 290, 240, 400, 30 );
		successMessage.setVisible( false );
		add( successMessage );
	}

	protected void AddCharges() {
		
		Charge real = new Charge( 100, 250, -3.25 ); //the real charge
		real.SetMobile( false );
		
		Charge image = new Charge( 600, 365, 3 ); //the image charge
		
		p.add( real );
		p.add( image ); 
		
	}
	
	protected void PaintLines(Graphics g) {
		
		//drawing conducting surface
		
		g.drawOval( 190, 100, 300, 300 );
		
		//defining center of circle
		Point center = new Point( 340, 250 );
		
		//container for current point
		Point cp = new Point( 0, 0 );
		
		//drawing lines. For each pixel test if it is in the sphere or not, and paint accordingly
		for ( int x = 1; x < width; x++ ) {
			for ( int y = 1; y < height; y++ ) {
				cp.setLocation( x, y );
			
				//if thread is inside sphere, and x or y is at an odd number, 
				//or if success has been reached, do not paint
				if ( cp.distance( center ) < 150 && ( x % 2 == 1 || y % 2 == 1 || success == true )  ) {
					continue;
				}
				
				if ( (int)( p.V( x, y )*3000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
			}
		}
	
	}

	
	private void SuccessCheck() {
		
		if ( p.get( 1 ).GetCharge() == targetCharge && p.get( 1 ).distance( targetPoint ) < 5 ) {
			
			//reaction to success: paint only physical lines
			success = true;
			successMessage.setVisible( true );
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
			chargeSlider.setValue( 2 );
			success = false;
			successMessage.setVisible( false );
			repaint();
		}
	}

	//reaction to a charge being dropped
	public void mouseReleased ( MouseEvent e ) {
		drag = -1;
		SuccessCheck();
	}
	
	//reaction to charge slider being slid
	public void stateChanged( ChangeEvent e ) {
		p.get( 1 ).SetCharge( ( (JSlider)( e.getSource() ) ).getValue() );
		SuccessCheck();
		repaint();
	}
	
	
}
