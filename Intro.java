
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    PlanePanel subclass which does a short animation to introduce how the game works.
    
 */

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;


public class Intro extends PlanePanel {

	//feilds: 
	
	//labels containing instructions
	private JLabel text1;
	private JLabel text2;
	private JLabel text3;
	private JLabel text4;
	private JLabel text5;
	
	private Timer rolex = new Timer(); //used to manage the sequence of appearing instructions
	
	private int stage = 0; //the stage the introduction animation is currently at
	
	JButton skip; //button which skips introduction
	
	//constructor
	public Intro( ImageFrame a ) {
		super(a);
	}
	
	//getter for an instance of skip button
	public JButton GetSkip () {
		return skip;
	}
	
	protected void AddButtons() {
		
		setLayout( null );
		
		skip = new JButton( "skip intro" );
		skip.setBounds( 600, 470, 100, 30 );
		skip.addActionListener( a ); //moves onto the first problem
		skip.addMouseListener( this ); //and stops the timer
		add(skip);
		
		JLabel image = new JLabel( "  Image Charge" );
		image.setBounds( 550, 310, 150, 40 );
		add( image );
		
		text1 = new JLabel( "a charge is close to a conducting surface" );
		text1.setBounds( 110, 190, 400, 30 );
		text1.setVisible( false );
		add( text1 );
		
		text2 = new JLabel( "the conducting surface must be an equipotential" );
		text2.setBounds( 90, 250, 400, 30 );
		text2.setVisible( false );
		add( text2 );
		
		text3 = new JLabel( "chose a strength for the  image charge" );
		text3.setBounds( 345, 350, 400, 30 );
		text3.setVisible( false );
		add( text3 );
		
		text4 = new JLabel( "and drag it to where you want in the unphysical region" );
		text4.setBounds( 75, 365, 400, 30 );
		text4.setVisible( false );
		add( text4 );
		
		text5 = new JLabel( "to match a level surface with the conducting surface" );
		text5.setBounds( 80, 300, 400, 30 );
		text5.setVisible( false );
		add( text5 );
	}
	
	protected void AddCharges() {
		
		Charge real = new Charge( 250, 125, 4 ); //the real charge
		real.SetMobile( false );
		
		Charge image = new Charge( 600, 365, -2 ); //the image charge
		image.SetMobile( false );
		
		p.add( real );
		p.add( image ); 
	}
	
	protected void PaintLines( Graphics g ) {
		
		//drawing conducting surface
		g.fillRect(  0, 248, 500, 4 );
		
		if ( stage >= 5 ) {
			//drawing field lines in physical region (bold)
			for ( int x = 1; x < width; x++ ) {
				for ( int y = 1; y < height/2; y++ ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
				
			//drawing field lines in unphysical region (light)
			for ( int x = 1; x < width; x += 2 ) {
				for ( int y = height/2; y < height; y += 2 ) {
					if ( (int)( p.V( x, y )*2000 ) % 10 == 0 ) { g.fillOval( x, y, 2, 2 ); }
				}
			}
		}
		
	}
	
	
	//this will show the sequence of messages with delays between each of them explaining what to do
	public void RunIntro () {
		text1.setVisible( true );
		rolex.schedule( new IntroTask(), 0, 4000 );
	}
	
	
	//reaction to skip button being pressed
	public void mouseClicked(MouseEvent e) {
		if ( e.getSource() == skip ) {
			rolex.cancel();
		}
	}
	
	
	//nested class:
	//This is called by the timer in intro to move between stages in the introduction animation
	public class IntroTask extends TimerTask {
		
		
		//reaction to timer calling this object to carry out the relevant task
		public void run() {

			//if the time has come to show the sequence of instructions
			if ( stage == 1 ) {
				text1.setVisible( false );
				text2.setVisible( true );
			}
			
			if ( stage == 2 ) {
				text2.setVisible( false );
				text3.setVisible( true );
			}
			
			if ( stage == 3 ) {
				text3.setVisible( false );
				text4.setVisible( true );
			}
			
			//if the time has come in the animation to draw the level surfaces:
			if ( stage == 4 ) {
				text4.setVisible( false );
				text5.setVisible( true );
			}
			
			if ( stage == 5 ) {
				rolex.cancel();
				skip.doClick();
			}
			
			stage++;
			repaint();
		}
		
	}

	
}
