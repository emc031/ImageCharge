
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    The third PlanePanel subclass describing the third problem:
    
    A single positive charge next to two perpendicular conducting surfaces
 */

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;


public class Level3 extends PlanePanel {

	//fields
	private JButton l1; //buttons leading to the other levels
	private JButton l2;
	
	private JButton reset; //puts all charges into original position
	private JSlider chargeSlider1; //sliders which takes in chosen charge of image
	private JSlider chargeSlider2;
	private JSlider chargeSlider3;
	private JLabel successMessage; //message for when user wins level
	
	private boolean success = false; //set to true if the target charge & point is reached,
									//and commands paintComponent to paint success reaction
	
	//constructor
	public Level3 ( ImageFrame a ) {
		super( a );
	}
	
	//getters for level buttons
	
	public JButton GetL2Button() {
		return l2;
	}
		
	public JButton GetL1Button() {
		return l1;
	}
	
	protected void AddButtons () {
		
		setLayout( null );
		
		l1 = new JButton( "Problem 1" );
		l1.setBounds( 550, 10, 100, 40 );
		l1.addActionListener( a );
		add( l1 );
	
		l2 = new JButton( "Problem 2" );
		l2.setBounds( 550, 60, 100, 40 );
		l2.addActionListener( a );
		add( l2 );
		
		JLabel l3 = new JLabel( "Problem 3" );
		l3.setBounds( 570, 110, 100, 40 );
		add( l3 );
		
		reset = new JButton( "Reset" );
		reset.setBounds( 550, 450, 100, 40 );
		reset.addMouseListener( this );
		add( reset );
	
		chargeSlider1 = new JSlider( -6, 6, 2 );
		chargeSlider1.setBounds( 560, 250, 120, 40 );
		chargeSlider1.setPaintTicks( true );
		chargeSlider1.setMinorTickSpacing( 1 );
		chargeSlider1.setMajorTickSpacing( 6 );
		chargeSlider1.addChangeListener( this );
		add( chargeSlider1 );
		
		chargeSlider2 = new JSlider( -6, 6, 2 );
		chargeSlider2.setBounds( 560, 310, 120, 40 );
		chargeSlider2.setPaintTicks( true );
		chargeSlider2.setMinorTickSpacing( 1 );
		chargeSlider2.setMajorTickSpacing( 6 );
		chargeSlider2.addChangeListener( this );
		add( chargeSlider2 );
		
		chargeSlider3 = new JSlider( -6, 6, 2 );
		chargeSlider3.setBounds( 560, 370, 120, 40 );
		chargeSlider3.setPaintTicks( true );
		chargeSlider3.setMinorTickSpacing( 1 );
		chargeSlider3.setMajorTickSpacing( 6 );
		chargeSlider3.addChangeListener( this );
		add( chargeSlider3 );
		
		successMessage = new JLabel( "poisson's equation is now satisfied in the physical region" );
		successMessage.setBounds( 80, 300, 400, 30 );
		successMessage.setVisible( false );
		add( successMessage );
	}

	protected void AddCharges() {
		
		Charge real = new Charge( 125, 125, 2 ); 
		real.SetMobile( false );
		
		Charge image1 = new Charge( 540, 265, 2 ); 
		Charge image2 = new Charge( 540, 325, 2 );
		Charge image3 = new Charge( 540, 385, 2 );
		
		p.add( real );
		p.add( image1 );
		p.add( image2 );
		p.add( image3 );
		
	}
	
	protected void PaintLines(Graphics g) {
		
		//drawing conducting surface
		g.fillRect( 0, 248, 250, 4 );
		g.fillRect( 248, 0, 4, 250 );
		
		//drawing lines in physical region (bold)
		for ( int x = 1; x < width/2; x++ ) {
			for ( int y = 1; y < height/2; y++ ) {
				if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
			}
		}
		
		if ( success == false ) {
			
			//drawing lines in unphysical region (light)
			for ( int x = width/2; x < width; x += 2 ) {
				for ( int y = 1; y < height/2; y += 2 ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
			for ( int x = 1; x < width; x += 2 ) {
				for ( int y = height/2; y < height; y += 2 ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
		
		}
	}


	private void SuccessCheck() {
		
		//if not success, these will not be changed to true
		success = false;
		successMessage.setVisible( false );
		
		//test all possible permutations of success the user cound have acheived
				for ( int i1 = 1; i1 <= 3; i1++ ) {
					for ( int i2 = 1; i2 <= 3; i2++ ) {
						for ( int i3 = 1; i3 <= 3; i3++ ) {
							
					if ( p.get( i1 ).GetCharge() == -2 && p.get( i1 ).distance( new Point( 125, 375 ) ) < 20
						&& p.get( i2 ).GetCharge() == 2 && p.get( i2 ).distance( new Point( 375, 375 ) ) < 20
						&& p.get( i3 ).GetCharge() == -2 && p.get( i3 ).distance( new Point( 375, 125 ) ) < 20 ) {
										
				//reaction to success: paint only physical lines
				success = true;
				successMessage.setVisible( true );
				repaint();
													
									}
						}
					}
				}
				
	}
	
	
	//event handling
	
	//reaction to the reset button being pressed
	public void mouseClicked( MouseEvent e ) {
		if ( e.getSource() == reset ) {
			p.clear();
			AddCharges();
			chargeSlider1.setValue( 2 );
			chargeSlider2.setValue( 2 );
			chargeSlider3.setValue( 2 );
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
		
		Object source = e.getSource();
		
		if ( source == chargeSlider1 ) {
			p.get( 1 ).SetCharge( ( (JSlider)( source ) ).getValue() );
		}
		if ( source == chargeSlider2 ) {
			p.get( 2 ).SetCharge( ( (JSlider)( source ) ).getValue() );
		}
		if ( source == chargeSlider3 ) {
			p.get( 3 ).SetCharge( ( (JSlider)( source ) ).getValue() );
		}
		
		SuccessCheck();
		
		repaint();
	}
	
	
}
