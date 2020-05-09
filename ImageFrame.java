
/*  author: Euan McLean, University of Edinburgh
	date: August 2012
	version: 1.1

    The frame which contains the Image Charge Game.
    
    The Image Charge Game contains 3 levels where an arrangement of charge(s) and
    conducting surface(s) are presented along with level surfaces of the electrostatic
    potential. The user must chose an appropriate charge for an image charge and drag it
    onto the simulation in order to make the conductor surfaces equipotential.
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ImageFrame extends JFrame implements ActionListener {

	//fields: the 4 panels which contain the game
	private Intro i;
	private Level1 l1;
	private Level2 l2;
	private Level3 l3;
	
	//called when applet launches
	public ImageFrame () {
	
		setBackground( Color.white );
		setSize( 700, 520 );

		//constructing levels
		l1 = new Level1( this );
		l2 = new Level2( this );
		l3 = new Level3( this );
		
		//constructing and running intro
		i = new Intro( this );
		getContentPane().add( i );
		
		setVisible( true );
		i.RunIntro();
		
	}

	//event handling:
	
	//for when a button to bring up a new level/panel is pressed
	public void actionPerformed (ActionEvent e ) {
		
		Object source = e.getSource();
		
		if ( source == i.GetSkip() ) {
			i.setVisible( false );
			getContentPane().add( l1 );
		}
		
		if ( source == l1.GetL2Button() ) {
			l1.setVisible( false );
			l2.setVisible( true );
			getContentPane().add( l2 );
		}
		
		if ( source == l1.GetL3Button() ) {
			l1.setVisible( false );
			l3.setVisible( true );
			getContentPane().add( l3 );
		}
		
		if ( source == l2.GetL1Button() ) {
			l2.setVisible( false );
			l1.setVisible( true );
			getContentPane().add( l1 );
		}
		
		if ( source == l2.GetL3Button() ) {
			l2.setVisible( false );
			l3.setVisible( true );
			getContentPane().add( l3 );
		}
		
		if ( source == l3.GetL1Button() ) {
			l3.setVisible( false );
			l1.setVisible( true );
			getContentPane().add( l1 );
		}
		
		if ( source == l3.GetL2Button() ) {
			l3.setVisible( false );
			l2.setVisible( true );
			getContentPane().add( l2 );
		}
	}
	
}
