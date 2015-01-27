/**
 * @(#)chartApplet.java
 *
 * chartApplet 
 *
 * @author 
 * @version 0.98010/2
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object.*;
import java.awt.Color.*;


public class chartApplet extends JApplet {

	Canvas canvas;
	chartData cdo;
	String chartType;
	Color[] myColors;
	Color customC1 = new Color(47,79,47);
	Color customC2 = new Color(255,69,0);
	Color customC3 = new Color(159,121,238);
	Color customC4 = new Color(79,47,79);

	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			int w = getWidth();
			int h = getHeight();
			double sum = 0;
			int pieDegrees = 0;
			int startDegree = 0;
			int numerator = 0;
			int xLocation = 20;
			int barHeight = 0;
			int largestValue = 0;
			int maxHeight = h-((h*5)/6);
			double temp = 0;
			if (chartType.equals("PIE")) {

				// to do: add code to draw pie chart
				
				int[] dat = cdo.getArray();
				for(int i = 0; i < dat.length; i++){
					sum = sum + dat[i];
				}
				
				for(int i = 0; i < dat.length; i++){
					numerator = dat[i];
					pieDegrees =(int)Math.round((numerator*360/sum));
					g.setColor(myColors[i]);
					if ( i == dat.length -1)
						g.fillArc(20, 20, w-40, h-40, (int)startDegree, 360-startDegree); // makes sure there is no gap at the end;
					else
						g.fillArc(20, 20, w-40, h-40, (int)startDegree, (int)pieDegrees);
					startDegree = startDegree + pieDegrees;
					}
			} 
			else if (chartType.equals("BAR")) {
				

				
				int[] dat = cdo.getArray();
				for(int i = 0; i < dat.length; i++){
					if (largestValue < dat[i])
						largestValue = dat[i];
					                     
				}
				
				for(int i = 0; i < dat.length; i++){
					temp = dat[i]/(double)largestValue; // gives you a decimal style percentage 
					barHeight = (int)((h*5/6)*temp); // barHeight is now anywhere between 0-h*5/6, 
					g.setColor(myColors[i]);
					g.fillRect(xLocation, h-barHeight, (w-40)/dat.length, barHeight);
					xLocation = xLocation + ((w-40)/dat.length);
					}
				g.setColor(Color.black);
				g.drawLine(20, h, 20, maxHeight);
				g.setColor(Color.black);
				g.drawLine(20, h-1, w-20, h-1);
			}
			
		}
	}

	private class uiHandler implements ActionListener { // The event listener.
		public void actionPerformed(ActionEvent e) {
			String aC = e.getActionCommand();
			// System.out.println("UI"+aC);
			if (aC.equals("New Data")) {
				cdo.reset();
				canvas.repaint();
			} else if (aC.equals("Draw Pie")) {
				chartType = "PIE";
				canvas.repaint();
			} else if (aC.equals("Draw Bar")) {
				chartType = "BAR";
				canvas.repaint();
			}

		}
	}

	public void init() {  // initialize applet

		chartType = "BAR";  // PIE chart to start with
		createComponents();  // build the UI
		chooseColors();      // create colors
		cdo = new chartData();  // initialize chartData object
	}

	public void chooseColors() {
		myColors = new Color[12];
		
		myColors[0] = Color.blue;
		myColors[1] = Color.red;
		myColors[2] = Color.green;
		myColors[3] = Color.cyan;
		myColors[4] = Color.magenta;
		myColors[5] = Color.orange;
		myColors[6] = Color.yellow;
		myColors[7] = Color.pink;
		myColors[8] = customC1;
		myColors[9] = customC2;
		myColors[10] = customC3;
		myColors[11] = customC4;

	}
	public void createComponents() { 
		
		
		setSize(400, 400); // size of applet
		JPanel content = new JPanel(); // panel to contain all of the applet
										// content
		content.setLayout(new BorderLayout()); // how components are
												// auto-arranged

		JPanel uip = new JPanel(); // panel to contain the UI - buttons
		uip.setLayout(new FlowLayout()); // how buttons are auto-arranged

		// create the buttons
		JButton b1 = new JButton("New Data");
		JButton b2 = new JButton("Draw Pie");
		JButton b3 = new JButton("Draw Bar");
		
		// add buttons to the interface panel
		uip.add(b1);
		uip.add(b2);
		uip.add(b3);
		// create a listener object to listen for button events
		uiHandler uih = new uiHandler();
		// connect listener to button events
		b1.addActionListener(uih);
		b2.addActionListener(uih);
		b3.addActionListener(uih);

		// create a graphics area
		canvas = new Canvas();

		// add panels to content pane

		content.add(canvas, BorderLayout.CENTER);
		content.add(uip, BorderLayout.SOUTH);

		setContentPane(content); // 

	}  

	private class chartData {
		private int[] dataValues; // array to store the data

		public chartData() { // default constructor
			reset(); // just call reset to do the work
		}

		public void reset() { // create a new random data set
			double dn = Math.random() * 6; // choose a data set size from 4 to 9
											// values
			int n = 4 + (int) dn;
			dataValues = new int[n]; // allocate array

			// choose one of three range sizes
			int rangeChoice = (int) (Math.random() * 3);
			double range;
			if (rangeChoice == 0)
				range = 10;
			else if (rangeChoice == 1)
				range = 100;
			else
				range = 1000;
			for (int i = 0; i < n; i++) {
				dataValues[i] = (int) ((3 + 7 * Math.random()) * range);
			}
		}

		public int[] getArray() { // return data array
			return dataValues;
		}

	} // end chartData class

}
